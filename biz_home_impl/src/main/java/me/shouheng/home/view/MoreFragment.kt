package me.shouheng.home.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import me.shouheng.home.R
import me.shouheng.home.databinding.HomeFragmentMoreBinding
import me.shouheng.home.event.SimpleEvent
import me.shouheng.home.vm.OptionsViewModel
import me.shouheng.home.widget.confirmOrCancel
import me.shouheng.home.widget.simpleDialogContent
import me.shouheng.home.widget.simpleDialogTitle
import me.shouheng.uix.common.anno.DialogStyle
import me.shouheng.uix.widget.dialog.content.SimpleEditor
import me.shouheng.uix.widget.dialog.content.simpleEditor
import me.shouheng.uix.widget.dialog.createDialog
import me.shouheng.uix.widget.dialog.showMessage
import me.shouheng.utils.constant.ActivityDirection
import me.shouheng.utils.ktx.onDebouncedClick
import me.shouheng.utils.ktx.stringOf
import me.shouheng.utils.ktx.toast
import me.shouheng.utils.permission.Permission
import me.shouheng.utils.store.PathUtils
import me.shouheng.vmlib.anno.FragmentConfiguration
import me.shouheng.vmlib.base.CommonFragment
import me.shouheng.vmlib.comn.ContainerActivity
import me.shouheng.vmlib.component.observeOn
import me.shouheng.vmlib.component.post
import me.shouheng.vmlib.network.NetworkStateManager
import me.shouheng.vmlib.network.download.DownloadListener
import me.shouheng.vmlib.network.download.Downloader
import java.io.File

/**
 * Sample fragment for more features. This is the fragment extend from [CommonFragment] by databinding.
 *
 * @author ShouhengWang 2019-6-29
 */
@FragmentConfiguration(shareViewModel = true, useEventBus = true)
class MoreFragment : CommonFragment<OptionsViewModel, HomeFragmentMoreBinding>(), ContainerActivity.BackEventResolver {

    private val downloadUrl = "https://images.unsplash.com/photo-1501879779179-4576bae71d8" +
            "d?ixlib=rb-1.2.1&q=80&fm=jpg&crop=entropy&cs=tinysrgb&dl=vladimir-riabinin-diMBLU4FzDQ-unsplash.jpg"

    override fun getLayoutResId(): Int = R.layout.home_fragment_more

    override fun doCreateView(savedInstanceState: Bundle?) {
        initViews()
        observes()
    }

    @SuppressLint("MissingPermission")
    private fun initViews() {
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.title = stringOf(R.string.main_more_widget)

        binding.btnCheckPermission.onDebouncedClick {
            checkPermission(Permission.CAMERA) {
                toast(R.string.main_more_widget_check_permission_granted)
            }
        }
        binding.btnDownload.onDebouncedClick {
            Downloader.getInstance()
                .setOnlyWifi(true)
                .download(downloadUrl, PathUtils.getExternalStoragePath(), object :
                    DownloadListener {
                    override fun onError(errorCode: Int) {
                        toast("Download : error $errorCode")
                    }

                    override fun onStart() {
                        toast("Download : start")
                    }

                    override fun onProgress(readLength: Long, contentLength: Long) {
                        toast("Download : onProgress $readLength/$contentLength")
                    }

                    override fun onComplete(file: File?) {
                        toast("Download : complete : ${file?.absoluteFile}")
                    }
                })
        }
        binding.btnPref.onDebouncedClick {
            ContainerActivity.open(SamplePreference::class.java)
                .put(ContainerActivity.KEY_EXTRA_THEME_ID, R.style.RedAppTheme)
                .put(ContainerActivity.KEY_EXTRA_ACTIVITY_DIRECTION, ActivityDirection.ANIMATE_SLIDE_BOTTOM_FROM_TOP)
                .withDirection(ActivityDirection.ANIMATE_SLIDE_TOP_FROM_BOTTOM)
                .launch(requireContext())
        }
        binding.btnCrash.onDebouncedClick { 1/0 }
        binding.btnVmPost.onDebouncedClick {
            post(SimpleEvent(stringOf(R.string.main_more_widget_make_a_post_message)))
        }
        binding.btnLongTask.onDebouncedClick {
            vm.doLongTask()
        }
        binding.btnShowDialog.onDebouncedClick {
            createDialog {
                withTitle(simpleDialogTitle(requireContext(), stringOf(R.string.main_dialog_input_title)))
                withContent(simpleEditor {
                    withSingleLine(true)
                    withHint(stringOf(R.string.main_dialog_input_hint))
                })
                withBottom(confirmOrCancel(requireContext()) {
                    (it as? SimpleEditor)?.let {
                        requireContext().showMessage {
                            withMessage(it.getContent().toString())
                        }
                    }
                })
            }.show(childFragmentManager, "input-dialog")
        }
        binding.btnTickTick.onDebouncedClick {
            vm.doTickTick()
        }
        binding.fab.onDebouncedClick {
            activity?.recreate()
        }
    }

    private fun observes() {
        observeOn(vm.longTaskLiveData) {
            onSuccess {
                binding.btnLongTask.text = it.data
            }
            onLoading {
                toast(R.string.main_more_widget_long_task_executing)
            }
        }
        observeOn(vm.bindLiveData) {
            onProgress {
                toast("Progress: ${it.data}")
            }
            onSuccess {
                toast("Done: ${it.data}")
            }
        }
        // Register the network state.
        NetworkStateManager.observe(this, false) {
            toast(stringOf(R.string.main_more_widget_network_state_changed).format("${it.connected}"))
        }
    }

    override fun onBackPressed(activity: ContainerActivity) {
        createDialog {
            withStyle(DialogStyle.STYLE_WRAP)
            withContent(simpleDialogContent(stringOf(R.string.main_more_widget_back_tips)))
            withBottom(confirmOrCancel(requireContext()) {
                activity.superOnBackPressed()
            })
        }.show(childFragmentManager, "input-dialog")
    }
}
