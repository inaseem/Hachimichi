package ali.naseem.hachimichi.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import java.util.List;

import ali.naseem.hachimichi.R;
import ali.naseem.hachimichi.models.ImageModel;

public class SliderAdapter extends PagerAdapter {

    private List<ImageModel> images;
    private Context context;

    public SliderAdapter(Context context, List<ImageModel> images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View myImageLayout = LayoutInflater.from(view.getContext()).inflate(R.layout.image_list_item, view, false);
        ImageView myImage = myImageLayout
                .findViewById(R.id.image);
        Glide.with(context)
                .load(images.get(position).getUrl())
                .into(myImage);
        view.addView(myImageLayout, 0);
        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, @NonNull Object object) {
        return view.equals(object);
    }
}
