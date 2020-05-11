package com.example.myapplication.subChapterDetail

import android.app.ActionBar
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.ShareCompat
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSubchapterDetailBinding
import com.example.myapplication.utilities.InjectorUtils


/**
 * A fragment representing a single Plant detail screen.
 */
class SubChapterDetailFragment : Fragment() {

    private val args: SubChapterDetailFragmentArgs by navArgs()

    private val subChapterDetailViewModel: SubChapterDetailViewModel by viewModels () {
        InjectorUtils.provideSubChapterDetailViewModelFactory(requireActivity(), args.subChapterId, args.parentChapterId)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentSubchapterDetailBinding = DataBindingUtil.inflate<FragmentSubchapterDetailBinding>(
            inflater, R.layout.fragment_subchapter_detail, container, false)
//        val result = AppDatabase.getInstance(requireContext()).chapterDao().getSubChapterr(args.subChapterId, args.parentChapterId)
//        val test = result.content
//        binding.plantDescription.setText(test)
//        val result = InjectorUtils.provideSubChapterDetailViewModelFactory(requireActivity(), args.subChapterId, args.parentChapterId)
        .apply {
            viewModel = subChapterDetailViewModel
            lifecycleOwner = viewLifecycleOwner

            var isToolbarShown = false

            // scroll change listener begins at Y = 0 when image is fully collapsed
            chapterDetailScrollview.setOnScrollChangeListener(
                NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->

                    // User scrolled past image to height of toolbar and the title text is
                    // underneath the toolbar, so the toolbar should be shown.
                    val shouldShowToolbar = scrollY > toolbar.height

                    // The new state of the toolbar differs from the previous state; update
                    // appbar and toolbar attributes.
                    if (isToolbarShown != shouldShowToolbar) {
                        isToolbarShown = shouldShowToolbar

                        // Use shadow animator to add elevation if toolbar is shown
                        appbar.isActivated = shouldShowToolbar

                        // Show the plant name if toolbar is shown
                        toolbarLayout.isTitleEnabled = shouldShowToolbar
                    }
                }
            )

            toolbar.setNavigationOnClickListener { view ->
                view.findNavController().navigateUp()
            }

            toolbar.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_share -> {
                        createShareIntent()
                        true
                    }
                    else -> false
                }
            }
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    // Helper function for calling a share functionality.
    // Should be used when user presses a share button/menu item.
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @Suppress("DEPRECATION")
    private fun createShareIntent() {
        val shareText = subChapterDetailViewModel.subChapter.value.let { subchapter ->
            if (subchapter == null) {
                ""
            } else {
                getString(R.string.share_message, subchapter.title)
            }
        }
        val shareIntent = activity?.let {
            ShareCompat.IntentBuilder.from(it)
                .setText(shareText)
                .setType("text/plain")
                .createChooserIntent()
                .addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
        }
        startActivity(shareIntent)
    }

}
