package net.lzzy.cinemanager.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import net.lzzy.cinemanager.R;
import net.lzzy.cinemanager.models.Cinema;
import net.lzzy.cinemanager.models.CinemaFactory;
import net.lzzy.sqllib.GenericAdapter;
import net.lzzy.sqllib.ViewHolder;

import java.util.List;

/**
 * Created by lzzy_gxy on 2019/3/27.
 * Description:
 */
public class CinemasFragment extends BaseFragment {

    private List<Cinema> cinemas;
    private CinemaFactory factory = CinemaFactory.getInstance();
    private ListView lv;
    private Cinema cinema;
    private GenericAdapter<Cinema> adapter;
    public CinemasFragment(Cinema cinema) {
        this.cinema=cinema;
    }
    public CinemasFragment(){

    }



    @Override
    protected void Populate() {
        lv = find(R.id.activity_cinemas_lv);
        View empty = find(R.id.activity_cinemas_tv_none);
        lv.setEmptyView(empty);
        cinemas = factory.get();
        adapter = new GenericAdapter<Cinema>(getActivity(), R.layout.cinema_item, cinemas) {
            @Override
            public void populate(ViewHolder holder, Cinema cinema) {
                holder.setTextView(R.id.activity_cinema_item_name, cinema.getName())
                        .setTextView(R.id.activity_cinema_item_area, cinema.getLocation());
            }

            @Override
            public boolean persistInsert(Cinema cinema) {
                return factory.addCinema(cinema);
            }

            @Override
            public boolean persistDelete(Cinema cinema) {
                return factory.deleteCinema(cinema);
            }
        };
        lv.setAdapter(adapter);
        if (cinema != null) {
            svae(cinema);
        }

    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_cincmas;
    }

    @Override
    public void sarch(String kw) {
        cinemas.clear();
        if (TextUtils.isEmpty(kw)) {
            cinemas.addAll(factory.get());
        } else {
            cinemas.addAll(factory.searchCinemas(kw));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void svae(Cinema cinema) {
        adapter.add(cinema);
    }

}
