package io.github.joseluiscd.seagull

import android.content.Context
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import io.github.joseluiscd.seagull.model.Track
import kotlinx.android.synthetic.main.fragment_track_context_menu.*


/**
 *
 * A fragment that shows a list of items as a modal bottom sheet.
 *
 * You can show this modal bottom sheet from your activity like this:
 * <pre>
 *    TrackContextMenuFragment.newInstance(30).show(supportFragmentManager, "dialog")
 * </pre>
 *
 * You activity (or fragment) needs to implement [TrackContextMenuFragment.Listener].
 */
class TrackContextMenuFragment : BottomSheetDialogFragment() {
    private var mListener: Listener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_track_context_menu, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val vgroup = view as ViewGroup
        for(i in (0 until vgroup.childCount)){
            val child = vgroup.getChildAt(i)

            child.setOnClickListener {
                callClickListener(child.id)
            }
        }

    }

    fun callClickListener(id: Int){
        mListener?.onContextMenuItemClicked(id)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val parent = parentFragment
        if (parent != null) {
            mListener = parent as? Listener
        } else {
            mListener = context as? Listener
        }
    }

    override fun onDetach() {
        mListener = null
        super.onDetach()
    }

    interface Listener {
        fun onContextMenuItemClicked(id: Int)
    }

    companion object {
        const val ARG_TRACK = "item_count"

        // TODO: Customize parameters
        fun newInstance(track: Track): TrackContextMenuFragment =
                TrackContextMenuFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable(ARG_TRACK, track)
                    }
                }

    }
}
