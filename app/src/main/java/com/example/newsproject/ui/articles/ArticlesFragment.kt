package com.example.newsproject.ui.articles

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsproject.AppDelegate
import com.example.newsproject.R
import com.example.newsproject.data.datasource.NetworkState
import com.example.newsproject.data.model.json.Article
import com.example.newsproject.ui.adapter.ArticlesDiffUtilsItemCallback
import com.example.newsproject.ui.adapter.PagedArticleAdapter
import com.example.newsproject.ui.articles.web.WebViewFragment
import kotlinx.android.synthetic.main.fragment_recycler_news.*
import javax.inject.Inject

class ArticlesFragment : Fragment(), ArticlesView, PagedArticleAdapter.OnItemClickListener {

    companion object {
        @JvmStatic
        fun newInstance(): ArticlesFragment {
            Log.d("TAG", "newInstance")
            return ArticlesFragment()
        }

    }

    @Inject
    lateinit var presenter: ArticlesPresenter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var articleAdapter: PagedArticleAdapter
    private var state: Parcelable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppDelegate.getInjector().buildFragmentComponent().inject(this)
        presenter.onAttach(this)
//        presenter.newsList.observe(this, Observer {
//            articleAdapter.submitList(it)
//        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (savedInstanceState != null) {
//            state = savedInstanceState.getParcelable("ListState")
        }
        return inflater.inflate(R.layout.fragment_recycler_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initAdapter()
        presenter.loadPagedArticle()
    }

    override fun onResume() {
        super.onResume()
//        state?.let {
//            layoutManager.onRestoreInstanceState(it)
//        }
//        Log.d("onResume", articleAdapter.itemCount.toString())
    }

    override fun onSaveInstanceState(outState: Bundle) {
        recycler?.let {
            outState.putParcelable("ListState", layoutManager.onSaveInstanceState())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
//        presenter.disposeAll()
        presenter.onDetach()
    }

    override fun showArticles(articles: PagedList<Article>) {
        articleAdapter.submitList(articles)
        Log.d("onResume", "showArticles " + articleAdapter.itemCount.toString())
        Log.d("onResume", "showArticles " + articleAdapter.toString())
    }

    override fun onItemClick(url: String) {
        presenter.openWebView(url)
    }

    override fun openWebViewFragment(url: String) {
        if (url.isEmpty()) {
            Toast.makeText(requireContext(), getString(R.string.web_error), Toast.LENGTH_SHORT).show()
            return
        }
        requireActivity().supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.root_view, WebViewFragment.newInstance(url))
            .commit()
    }

    private fun initAdapter() {
        articleAdapter = PagedArticleAdapter(ArticlesDiffUtilsItemCallback(), this) {
            presenter.retry()
        }
        layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        recycler.layoutManager = layoutManager
        recycler.adapter = articleAdapter
        presenter.getNetworkState().observe(this, Observer<NetworkState> { articleAdapter.setNetworkState(it) })

    }
}