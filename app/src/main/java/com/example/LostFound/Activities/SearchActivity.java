package com.example.LostFound.Activities;

import androidx.appcompat.app.AppCompatActivity;

public class SearchActivity extends AppCompatActivity {

    /*private ActivitySearchBinding binding;
    private List<LostPost> cocktails;
    private FirebaseFirestore database;
    private String uid;
    Integer i;
    Integer adapterStatus = 0;

    private final RecyclerViewPostsInterface cocktailListener = new RecyclerViewPostsInterface() {
        @Override
        public void onPostClicked(LostPost posts) {
            Intent intent = new Intent(binding.getRoot().getContext(), CocktailPageActivity.class);
            intent.putExtra(, posts.id);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        listeners();
        AlwaysOnRun.AlwaysRun(this);
    }

    private void init() {
        uid = FirebaseAuth.getInstance().getUid();
        database = FirebaseFirestore.getInstance();
        getCocktails();
    }


    private void listeners() {
        binding.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                List<Cocktail> searchResult = SearchHelper.searchInCocktails(charSequence.toString(), cocktails);
                changeAdapter(adapterStatus, searchResult);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void getCocktails() {
        cocktails = new LinkedList<>();
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.textErrorMessage.setVisibility(View.INVISIBLE);
        binding.cocktailsRecyclerView.setVisibility(View.INVISIBLE);
        cocktails = new LinkedList<>();
        i = 0;
        database.collection("LostPost")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot document : queryDocumentSnapshots) {
                            i++;
                            String cocktailName = document.getString();
                            String creator = document.getString();
                            ArrayList<String> tags = (ArrayList<String>) document.get();
                            LostPost a = new LostPost(document.getId(), cocktailName, recipe, image, rating, creator, rating_count, tags);
                            cocktails.add(a);
                            if (i == queryDocumentSnapshots.size()) {
                                changeAdapter(0, cocktails);
                            }
                        }
                    }
                });
    }

    private void changeAdapter(Integer k, List<Cocktail> cocktails) {
        if (cocktails.isEmpty()) {
            binding.cocktailsRecyclerView.setVisibility(View.INVISIBLE);
            binding.progressBar.setVisibility(View.INVISIBLE);
            binding.textErrorMessage.setVisibility(View.VISIBLE);
        } else {
            if (k == 0) {
                CocktailsAdapter adapter = new CocktailsAdapter(cocktails, cocktailListener);
                binding.cocktailsRecyclerView.setAdapter(adapter);
            } else {
                CocktailsSingleAdapter adapter = new CocktailsSingleAdapter(cocktails, cocktailListener);
                binding.cocktailsRecyclerView.setAdapter(adapter);
            }
            binding.cocktailsRecyclerView.setVisibility(View.VISIBLE);
            binding.progressBar.setVisibility(View.INVISIBLE);
            binding.textErrorMessage.setVisibility(View.INVISIBLE);
        }
    }*/
}