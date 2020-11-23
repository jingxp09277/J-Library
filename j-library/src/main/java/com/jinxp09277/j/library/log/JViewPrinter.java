package com.jinxp09277.j.library.log;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jinxp09277.j.library.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LWB on 2020/10/28
 * JViewPrinter 将log显示在界面上
 */
public class JViewPrinter implements JLogPrinter {
    private RecyclerView recyclerView;
    private LogAdapter adapter;
    private JViewPrinterProvider viewProvider;

    public JViewPrinter(Activity activity) {
        FrameLayout rootView = activity.findViewById(android.R.id.content);
        recyclerView = new RecyclerView(activity);
        adapter = new LogAdapter(LayoutInflater.from(recyclerView.getContext()));
        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        viewProvider = new JViewPrinterProvider(rootView, recyclerView);
    }

    /**
     * 获取ViewProvider,通过ViewProvider可以控制log试图的展示和隐藏
     *
     * @return
     */
    @NonNull
    public JViewPrinterProvider getViewProvider() {
        return viewProvider;
    }

    @Override
    public void print(@NonNull JLogConfig config, int level, String tag, @NonNull String printString) {
        adapter.addItem(new JLogMo(System.currentTimeMillis(), level, tag, printString));
        //滚动到对应位置
        recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
    }

    private static class LogAdapter extends RecyclerView.Adapter<LogViewHolder> {
        private LayoutInflater inflater;
        private List<JLogMo> logs = new ArrayList<>();

        public LogAdapter(LayoutInflater inflater) {
            this.inflater = inflater;
        }

        void addItem(JLogMo logItem) {
            logs.add(logItem);
            notifyItemInserted(logs.size() - 1);
        }

        @NonNull
        @Override
        public LogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = inflater.inflate(R.layout.jlog_item, parent, false);
            return new LogViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull LogViewHolder holder, int position) {
            JLogMo logItem = logs.get(position);
            int color = getHighlightColor(logItem.level);
            holder.tagView.setTextColor(color);
            holder.messageView.setTextColor(color);

            holder.tagView.setText(logItem.getFlattened());
            holder.messageView.setText(logItem.log);
        }

        /**
         * 根据log级别获取不同高亮的颜色
         *
         * @param logLevel
         * @return
         */
        private int getHighlightColor(int logLevel) {
            int highlight;
            switch (logLevel) {
                case JLogType.V:
                    highlight = 0xffbbbbbb;
                    break;
                case JLogType.D:
                    highlight = 0xffffffff;
                    break;
                case JLogType.I:
                    highlight = 0xff6a8759;
                    break;
                case JLogType.W:
                    highlight = 0xffbbb529;
                    break;
                case JLogType.E:
                    highlight = 0xffff6b68;
                    break;
                default:
                    highlight = 0xffffff00;
                    break;
            }
            return highlight;
        }

        @Override
        public int getItemCount() {
            return logs.size();
        }
    }

    private static class LogViewHolder extends RecyclerView.ViewHolder {
        TextView tagView;
        TextView messageView;

        public LogViewHolder(@NonNull View itemView) {
            super(itemView);
            tagView = itemView.findViewById(R.id.tag);
            messageView = itemView.findViewById(R.id.message);
        }
    }
}
