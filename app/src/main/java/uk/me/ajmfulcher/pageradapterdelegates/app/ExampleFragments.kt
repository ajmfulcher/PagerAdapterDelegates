package uk.me.ajmfulcher.pageradapterdelegates.app

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import kotlinx.android.synthetic.main.fragment_example.*

class StartFragment : Fragment() {

    companion object {
        fun newInstance(): Fragment = StartFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_example, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textView.text = "Start"
    }

}

class WithStringPayloadFragment : Fragment() {

    companion object {
        private val EXTRA_PAYLOAD = "payload"
        fun newInstance(payload: String): Fragment {
            val fragment = WithStringPayloadFragment()
            val args = Bundle()
            args.putString(EXTRA_PAYLOAD, payload)
            fragment.setArguments(args)
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_example, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textView.text = arguments?.getString(EXTRA_PAYLOAD)
    }

}

class EndFragment : Fragment() {

    companion object {
        fun newInstance(): Fragment = EndFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_example, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textView.text = "End"
    }

}
