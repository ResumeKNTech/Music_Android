package com.android_app.music.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android_app.music.R;
import com.android_app.music.constant.GlobalFunction;
import com.android_app.music.databinding.ItemContactBinding;
import com.android_app.music.model.Contact;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private Context context;
    private final List<Contact> listContact;
    private final ICallPhone iCallPhone;

    public interface ICallPhone {
        void onClickCallPhone();
    }

    public ContactAdapter(Context context, List<Contact> listContact, ICallPhone iCallPhone) {
        this.context = context;
        this.listContact = listContact;
        this.iCallPhone = iCallPhone;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemContactBinding itemContactBinding = ItemContactBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ContactViewHolder(itemContactBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact contact = listContact.get(position);
        if (contact == null) {
            return;
        }
        holder.mItemContactBinding.imgContact.setImageResource(contact.getImage());
        switch (contact.getId()) {

            case Contact.GMAIL:
                holder.mItemContactBinding.tvContact.setText(context.getString(R.string.label_gmail));
                break;


        }

        holder.mItemContactBinding.layoutItem.setOnClickListener(v -> {
            switch (contact.getId()) {


                case Contact.HOTLINE:
                    iCallPhone.onClickCallPhone();
                    break;

                case Contact.GMAIL:
                    GlobalFunction.onClickOpenGmail(context);
                    break;


            }
        });
    }

    @Override
    public int getItemCount() {
        return null == listContact ? 0 : listContact.size();
    }

    public void release() {
        context = null;
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {

        private final ItemContactBinding mItemContactBinding;

        public ContactViewHolder(ItemContactBinding itemContactBinding) {
            super(itemContactBinding.getRoot());
            this.mItemContactBinding = itemContactBinding;
        }
    }
}