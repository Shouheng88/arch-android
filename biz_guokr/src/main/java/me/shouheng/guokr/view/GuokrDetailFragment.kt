package me.shouheng.guokr.view

import android.os.Bundle
import me.shouheng.guokr.api.bean.GuokrNewsContent
import me.shouheng.guokr.databinding.GuokrFragmentDetailBinding
import me.shouheng.guokr.vm.GuokrDetailViewModel
import me.shouheng.utils.ktx.toast
import me.shouheng.vmlib.base.ViewBindingFragment
import me.shouheng.vmlib.component.observeOn

/**
 * Guokr detail fragment.
 *
 * @Author wangshouheng
 * @Time 2021/9/30
 */
class GuokrDetailFragment : ViewBindingFragment<GuokrDetailViewModel, GuokrFragmentDetailBinding>() {

    override fun doCreateView(savedInstanceState: Bundle?) {
        vm.newsId = arguments?.getInt("news_id") ?: 0
        observeOn(GuokrNewsContent::class.java) {
            onSuccess { updateContent(it.data) }
            onFail { toast(it.message) }
        }
        vm.getNewsDetail()
    }

    private fun updateContent(content: GuokrNewsContent) {
        val body: String = content.result?.content?:""
        val css = "<div class=\"article\" id=\"contentMain\">"
        val result = """<!DOCTYPE html>
        <html lang="ZH-CN" xmlns="http://www.w3.org/1999/xhtml">
        <head>
        <meta charset="utf-8" />
        
        <link rel="stylesheet" href="file:///android_asset/guokr_master.css" />
        $css<script src="file:///android_asset/guokr.base.js"></script>
        <script src="file:///android_asset/guokr.articleInline.js"></script><script>
        var ukey = null;
        </script>
        
        </head>
        <div class="content" id="articleContent"><body>
        $body
        </div></body>
        </html>"""
        binding?.wv?.loadDataWithBaseURL("x-data://base", result, "text/html", "utf-8", null)
    }
}