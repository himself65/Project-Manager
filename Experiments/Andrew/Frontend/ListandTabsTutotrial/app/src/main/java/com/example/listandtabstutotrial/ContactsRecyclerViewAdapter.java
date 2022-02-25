package com.example.listandtabstutotrial;

import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.listandtabstutotrial.placeholder.PlaceholderContent.PlaceholderItem;
import com.example.listandtabstutotrial.databinding.FragmentSingleContactBinding;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ContactsRecyclerViewAdapter extends RecyclerView.Adapter<ContactsRecyclerViewAdapter.ViewHolder> {

    private final List<PlaceholderItem> mValues;

    public ContactsRecyclerViewAdapter(List<PlaceholderItem> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ViewHolder viewHolder = new ViewHolder(FragmentSingleContactBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

        Dialog dialog = new Dialog(parent.getContext());
        dialog.setContentView(R.layout.contact_modal);


        TextView modalDetatails = dialog.findViewById(R.id.textViewModel);
        modalDetatails.setMovementMethod(new ScrollingMovementMethod());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Toast.makeText(view.getContext(), "You just clicked" + viewHolder.mItem.details , Toast.LENGTH_SHORT).show();

                 modalDetatails.setText(viewHolder.mItem.details);
                 dialog.show();

             }
         });
         return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position).content);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;
        public PlaceholderItem mItem;

        public ViewHolder(FragmentSingleContactBinding binding) {
            super(binding.getRoot());
            mIdView = binding.itemNumber;
            mContentView = binding.content;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}