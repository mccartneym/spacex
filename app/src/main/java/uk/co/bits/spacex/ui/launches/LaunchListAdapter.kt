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
            .from(context)
            .inflate(R.layout.view_launch_details, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val launchItem = launchData[position]
        viewHolder.nameView.text = launchItem.name

        viewHolder.dateView.text = context.resources.getString(R.string.launch_date, launchItem.date)

        val url: String? = launchItem.smallImageUrl

        if (url != null) {
            Glide.with(context)
                .load(url)
                .placeholder(R.drawable.ic_baseline_downloading_24)
                .fitCenter()
                .into(viewHolder.imageView)
        } else {
            viewHolder.imageView.setImageResource(R.drawable.ic_baseline_image_not_supported_24)
        }

        val outcomeResId = when (launchItem.success) {
            true -> R.drawable.ic_baseline_check_24
            false -> R.drawable.ic_baseline_clear_24
            else -> R.drawable.ic_baseline_help_24
        }

        viewHolder.launchOutcome.setImageResource(outcomeResId)
    }

    override fun getItemCount(): Int {
        return launchData.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameView: TextView = view.findViewById(R.id.launch_name)
        val dateView: TextView = view.findViewById(R.id.launch_date)
        val imageView: ImageView = view.findViewById(R.id.launch_image)
        val launchOutcome: ImageView = view.findViewById(R.id.launch_outcome)
    }
}
