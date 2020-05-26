package com.techm.optustest.ui.userinfo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.techm.optustest.R
import com.techm.optustest.data.model.UserInfoResponseModel

/**adapter class for showing item in user fragment**/
class UserAdapter(
    private var userList: List<UserInfoResponseModel>,
    private var listener: OnItemClickListener
) : RecyclerView.Adapter<UserViewHolder>() {

    /**image click interface**/
    interface OnItemClickListener {
        fun onItemClick(item: UserInfoResponseModel?)
    }

    /**holder method for holding item view instance**/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        UserViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_user,
                parent,
                false
            )
        )

    /**get count of items in list**/
    override fun getItemCount() = userList.size

    /** bind item view**/
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.itemUserBinding.user = userList[position]
        holder.itemUserBinding.root.setOnClickListener {
            listener.onItemClick(holder.itemUserBinding.user)
        }
    }
}