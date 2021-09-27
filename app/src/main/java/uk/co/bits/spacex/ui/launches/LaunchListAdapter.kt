package uk.co.bits.spacex.ui.launches

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uk.co.bits.spacex.R
import uk.co.bits.spacex.data.model.Launch


class LaunchListAdapter(
    private val context: Context,
    private val launchData: List<Launch>
) : RecyclerView.Adapter<LaunchListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.view_launch_details, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val launchItem = launchData[position]
        viewHolder.textView.text = launchItem.rocketName
        val url: String = launchItem.smallImageUrl
        Glide.with(context)
            .load(url)
            .placeholder(R.drawable.ic_baseline_downloading_24)
            .fitCenter()
            .into(viewHolder.imageView)

        when (launchItem.success) {
            true -> viewHolder.launchOutcome.setImageResource(R.drawable.ic_baseline_check_24)
            false -> viewHolder.launchOutcome.setImageResource(R.drawable.ic_baseline_clear_24)
            else -> viewHolder.launchOutcome.setImageResource(R.drawable.ic_baseline_help_24)
        }
    }

    override fun getItemCount(): Int {
        return launchData.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.launch_name)
        val imageView: ImageView = view.findViewById(R.id.launch_image)
        val launchOutcome: ImageView = view.findViewById(R.id.launch_outcome)
    }
}
