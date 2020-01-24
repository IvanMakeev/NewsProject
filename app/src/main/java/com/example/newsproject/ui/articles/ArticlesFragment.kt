package com.example.newsproject.ui.articles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.newsproject.AppDelegate
import com.example.newsproject.R
import com.example.newsproject.data.model.room.ArticleRoom
import com.example.newsproject.data.repository.core.SearchResult
import com.example.newsproject.ui.adapter.ArticlesDiffUtilsItemCallback
import com.example.newsproject.ui.adapter.PagedArticleAdapter
import com.example.newsproject.ui.articles.contract.ArticlesViewContract
import com.example.newsproject.ui.articles.contract.WebViewContract
import kotlinx.android.synthetic.main.fragment_recycler_news.*
import javax.inject.Inject

class ArticlesFragment : Fragment(), ArticlesViewContract, PagedArticleAdapter.OnItemClickListener,
    SwipeRefreshLayout.OnRefreshListener {

    companion object {
        @JvmStatic
        fun newInstance(): ArticlesFragment {
            return ArticlesFragment()
        }
    }

    @Inject
    lateinit var presenter: ArticlesPresenter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var articleAdapter: PagedArticleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppDelegate.getInjector().buildFragmentComponent().inject(this)
        presenter.onAttach(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recycler_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initAdapter()
        refresher.setOnRefreshListener(this)
        presenter.loadArticles()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetach()
    }

    override fun showArticles(result: SearchResult<ArticleRoom>) {
        result.data.observe(this, Observer {
            articleAdapter.submitList(it)
        })

        result.networkState.observe(this, Observer {
            articleAdapter.setNetworkState(it)
        })
    }

    override fun onItemClick(url: String) {
        presenter.openWebView(url)
    }

    override fun onRefresh() {
        presenter.onItemsRefresh()
    }

    override fun refreshIsDone(flag: Boolean) {
        refresher.isRefreshing = !flag
    }

    override fun openWebViewFragment(url: String) {
        (requireActivity() as WebViewContract).openWebViewFragment(url)
    }

    override fun showWebViewErrorToast() {
        Toast.makeText(requireContext(), getString(R.string.web_error), Toast.LENGTH_SHORT).show()
    }

    private fun initAdapter() {
        articleAdapter = PagedArticleAdapter(ArticlesDiffUtilsItemCallback(), this) {
            presenter.retry()
        }
        layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        recycler.layoutManager = layoutManager
        recycler.adapter = articleAdapter
    }
}