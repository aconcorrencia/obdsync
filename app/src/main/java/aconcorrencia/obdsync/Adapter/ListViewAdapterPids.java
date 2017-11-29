package aconcorrencia.obdsync.Adapter;

import aconcorrencia.obdsync.ListViewObjPids;
import aconcorrencia.obdsync.R;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sillas on 19/09/2016.
 *
 * Adapter responsável por mostrar as Notificações do QuestiOnline
 *
 * Construtor: ArrayList<NotificacoesObj> mList (Lista com as notificações)
 *             ImageLoader mImageLoader (Objeto referente a imagem, caso vazio mostra o "Q" do QuestiOnline Informa)
 *
 *
 */
public class ListViewAdapterPids extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<ListViewObjPids> mList;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;
    private float scale;
    private int width;
    private int height;

    private final int VIEW_ITEM = 0;


    public ListViewAdapterPids(Context c, ArrayList<ListViewObjPids> mList) {
        mContext = c;
        this.mList = mList;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        scale = mContext.getResources().getDisplayMetrics().density;
        width = mContext.getResources().getDisplayMetrics().widthPixels - (int) (14 * scale + 0.5f);
        height = (width / 16) * 9;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View v = mLayoutInflater.inflate(R.layout.item_listview, viewGroup, false);
            MyViewHolderAmigo mvh = new MyViewHolderAmigo(v);
            return mvh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder myViewHolder, int position) {

            ((MyViewHolderAmigo) myViewHolder).txtTitle.setText(Html.fromHtml("<b>"+mList.get(position).getTitle()+"</b>"));
            ((MyViewHolderAmigo) myViewHolder).txtMsg.setText(Html.fromHtml("<i>" + mList.get(position).getDescription() + "</i> "));
            if(mList.get(position).getAvailable().equals("1")){
                ((MyViewHolderAmigo) myViewHolder).img.setImageResource(R.drawable.img_checked);
            }else{
                ((MyViewHolderAmigo) myViewHolder).img.setImageResource(R.drawable.img_unchecked);
            }
            /*if (!mList.get(position).getUrl_photo().equals("")) { //Se a questão tem imagem, mostra:
                //ConfigUrls.URL_IMG_QUESTOES +
                mImageLoader.displayImage(mList.get(position).getUrl_photo(), ((MyViewHolderAmigo) myViewHolder).img);
            } else { //Se não, mostra o "Q"
                ((MyViewHolderAmigo) myViewHolder).img.setImageResource(R.drawable.info2);
            }*/
    }

    @Override
    public int getItemCount() {
        return mList.size();

    }

    @Override
    public int getItemViewType(int position) {

            return VIEW_ITEM;

    }

    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r) {
        mRecyclerViewOnClickListenerHack = r;
    }


    public void addListItem(ListViewObjPids c, int position) {
        mList.add(c);
        notifyItemInserted(position);
    }

    public void addList(ArrayList<ListViewObjPids> c) {
        for (ListViewObjPids q : c) {
            mList.add(q);
            notifyItemInserted(mList.size());
        }
    }


    public void removeListItem(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
    }


    public class MyViewHolderAmigo extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView img;
        public TextView txtTitle;
        public TextView txtMsg;



        public MyViewHolderAmigo(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            txtMsg = (TextView) itemView.findViewById(R.id.txtMsg);
            itemView.setOnClickListener(this);
            itemView.setId(R.id.list_item);

        }

        @Override
        public void onClick(View v) {
            if (mRecyclerViewOnClickListenerHack != null) {
                mRecyclerViewOnClickListenerHack.onClickListener(v, getPosition());
            }
        }
    }

}
