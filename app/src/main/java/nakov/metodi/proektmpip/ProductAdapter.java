package nakov.metodi.proektmpip;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ImageViewHolder> {


    private Context mContext;
    private List<Product> mProducts;

    public ProductAdapter (Context context, List<Product> products){
        mContext = context;
        mProducts = products;
    }


    @NonNull
    @Override
    public ProductAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.product_item, viewGroup, false);

        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ImageViewHolder imageViewHolder, int position) {
        Product productCur = mProducts.get(position);
        imageViewHolder.prod_name.setText(productCur.getProduct_title());
        imageViewHolder.prod_price.setText(productCur.getProduct_price());
        Picasso.with(mContext)
                .load(productCur.getProduct_image())
                .placeholder(R.drawable.ic_image_black_24dp)
                .fit()
                .centerCrop()
                .into(imageViewHolder.prod_img);
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }



    ////////////////////////////////////////////////////////////////////////////////

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        public TextView prod_name, prod_price;
        public ImageView prod_img;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            prod_name = itemView.findViewById(R.id.prodName);
            prod_price = itemView.findViewById(R.id.prodPrice);
            prod_img = itemView.findViewById(R.id.prodImage);
        }
    }


///////////////////////////////////////////////////////////////////////////////




}
