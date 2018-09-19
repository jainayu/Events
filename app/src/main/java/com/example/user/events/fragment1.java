package com.example.user.events;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class fragment1 extends Fragment {

    ArrayAdapter<Event> adapter;
    List<Event> arrayList;
    FragmentInteractionListener listener;
    private RecyclerView rv;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    LayoutInflater layoutInflater;

    @Override
    public void onAttach(Context context) {
        if(context instanceof FragmentInteractionListener) {
            super.onAttach(context);
            listener = (FragmentInteractionListener)context;
        }
        else
            throw new IllegalStateException("context must be instance of FragmentInteractionListener");
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view= inflater.inflate(R.layout.fragment1,null);

        DatabaseReference reference = database.getReference().child("Events");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()) {

                    System.out.println(snapshot.getValue());

                    arrayList= new ArrayList<>();
                    arrayList.add(snapshot.getValue(Event.class));

                    rv= (RecyclerView) view.findViewById(R.id.recyclerView);
                    SimpleRVAdaptor srv = new SimpleRVAdaptor(arrayList);
                    rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                    rv.setAdapter(srv);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /** ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,str);
         ListView listView = (ListView)view.findViewById(R.id.list1);
         listView.setAdapter(adapter);
         return view;*/

        adapter=new ArrayAdapter<Event>(fragment1.this.getContext(),android.R.layout.simple_list_item_1,arrayList);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(),
                        recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
            @Override public void onItemClick(View view, int position) {
                listener.onEventSelect(arrayList.get(position));
                //AppCompatActivity activity = (AppCompatActivity)view.getContext();
                //activity.getSupportFragmentManager().beginTransaction().replace(R.id.frag1, new fragment2()).addToBackStack(null).commit();
            }

             @Override public void onLongItemClick(View view, int position) {
             // do whatever
             }
        }));

        return view;
    }

    private  class SimpleViewHolder extends RecyclerView.ViewHolder{

        private Event Event;
        private TextView eventName;
        private TextView organiser;
        private TextView date;
        private ImageView poster;

        public SimpleViewHolder (LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.events_name,parent,false));
            eventName = (TextView) itemView.findViewById(R.id.EventName);
            organiser = (TextView) itemView.findViewById(R.id.Organizer);
            date = (TextView) itemView.findViewById(R.id.Date);
            poster=(ImageView) itemView.findViewById(R.id.Poster);
        }

        public void bind (Event event){

            eventName.setText(event.getname());
            organiser.setText(event.getOrganizer());
            date.setText(event.getDate());

        }
    }

    private class SimpleRVAdaptor extends RecyclerView.Adapter<SimpleViewHolder>{
        private List<Event> Events;
        public SimpleRVAdaptor (List<Event> events){
            Events = events;

        }

        @NonNull
        @Override
        public SimpleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new SimpleViewHolder(LayoutInflater.from(parent.getContext()),parent);
        }

        @Override
        public void onBindViewHolder(@NonNull SimpleViewHolder holder, int position) {
            Event event = Events.get(position);
            holder.bind(event);
        }

        @Override
        public int getItemCount() {
            return Events.size();
        }
    }

    interface FragmentInteractionListener {
        void onEventSelect(Event event);
    }


}

