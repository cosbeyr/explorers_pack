package com.WWU.explorerspack.ui.guide.L3;

import androidx.annotation.NavigationRes;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Spanned;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.WWU.explorerspack.MainActivity;
import com.WWU.explorerspack.R;
import com.WWU.explorerspack.ui.guide.ChapterData.ChapterContent;
import com.WWU.explorerspack.ui.guide.JSONManager;

import org.json.JSONObject;
import org.w3c.dom.Text;

import io.noties.markwon.Markwon;
import us.feras.mdv.MarkdownView;

import static androidx.navigation.Navigation.findNavController;

public class SubChapterFragment extends Fragment {

    private SubChapterViewModel mViewModel;
    private String content = "Test Content";
    private String chapter = "Camouflage";
    private String subChapter = "Hiding";
    private EditText markdownEditText;
    private MarkdownView markdownView;
    private View rootView;
    private String searchKey;

    public static SubChapterFragment newInstance() {
        return new SubChapterFragment();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQuery(searchKey,true);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        searchKey = ((MainActivity) getActivity()).getCurrentSearch();
        if(getArguments() != null){
            chapter = getArguments().getString("chapter");
            subChapter = getArguments().getString("subChapter");
            content = JSONManager.getInstance(null).getSubChapter(chapter, subChapter);
        }
        ((MainActivity) getActivity()).setActionBarTitle(chapter + " > " + subChapter);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        rootView = inflater.inflate(R.layout.sub_chapter_fragment, container, false);
        ((MainActivity) getActivity()).setCurrentSubChapter(this);
        final TextView markdownView = rootView.findViewById(R.id.markdownView);
        markdownView.setMovementMethod(new ScrollingMovementMethod());
        final Markwon markwon = Markwon.create(getActivity());
        final Spanned markdown = markwon.toMarkdown(content);
        markwon.setParsedMarkdown(markdownView, markdown);
        return rootView;
    }

    public void scrollToPosition(final int position){
        final TextView markdownView = rootView.findViewById(R.id.markdownView);
        final ScrollView s = rootView.findViewById(R.id.scroll_view);
        // how to scroll
        s.post(new Runnable() {
            @Override
            public void run() {
                int line = markdownView.getLayout().getLineForOffset(position);
                int y = markdownView.getLayout().getLineTop(line); // e.g. I want to scroll to line 40
                s.scrollTo(0, y);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SubChapterViewModel.class);
        // TODO: Use the ViewModel
    }


}
