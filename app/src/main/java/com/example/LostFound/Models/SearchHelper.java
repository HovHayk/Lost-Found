package com.example.LostFound.Models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchHelper {
    /*public static List<LostPost> searchInCocktails(String s, List<LostPost> cocktails) {
        String a[] = s.split(" ");
        List<String> charsNormal = Arrays.asList(a);
        List<String> chars = new ArrayList<>();

        for (int i = 0; i < charsNormal.size(); i++) {
            chars.add(charsNormal.get(i).toLowerCase());
        }
        List<LostPost> finalList = new ArrayList<>();
        for (int i = 0; i < cocktails.size(); i++) {
            LostPost temp = cocktails.get(i);
            boolean added = false;
            for (int j = 0; j < chars.size(); j++) {
                if (temp.name.toLowerCase().contains(chars.get(j))) {
                    finalList.add(temp);
                    added = true;
                    break;
                }
                for (int z = 0; z < temp.tags.size(); z++) {
                    if (temp.tags.get(z).contains(chars.get(j))) {
                        finalList.add(temp);
                        added = true;
                        break;
                    }
                }
                if (added) {
                    break;
                }
            }
        }
        return finalList;
    }*/
}