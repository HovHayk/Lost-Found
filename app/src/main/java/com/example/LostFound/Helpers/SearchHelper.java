package com.example.LostFound.Helpers;

import com.example.LostFound.Models.FoundPost;
import com.example.LostFound.Models.LostPost;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchHelper {

    public static List<LostPost> searchInLostPosts(String s, List<LostPost> lostPosts) {
        String a[] = s.split(" ");
        List<String> charsNormal = Arrays.asList(a);
        List<String> chars = new ArrayList<>();

        for (int i = 0; i < charsNormal.size(); i++) {
            chars.add(charsNormal.get(i).toLowerCase());
        }

        List<LostPost> finalList = new ArrayList<>();

        for (int i = 0; i < lostPosts.size(); i++) {
            LostPost temp = lostPosts.get(i);
            boolean added = false;
            for (int j = 0; j < chars.size(); j++) {
                if (added) {
                    break;
                }
                if (temp.name.toLowerCase().contains(chars.get(j))) {
                    finalList.add(temp);
                    added = true;
                    break;
                }
                for (int z = 0; z < temp.getTags().indexOf(z); z++) {
                    if (temp.getTags().contains(chars.get(j))) {
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
    }


    public static List<FoundPost> searchInFoundPosts(String s, List<FoundPost> foundPosts) {
        String a[] = s.split(" ");
        List<String> charsNormal = Arrays.asList(a);
        List<String> chars = new ArrayList<>();

        for (int i = 0; i < charsNormal.size(); i++) {
            chars.add(charsNormal.get(i).toLowerCase());
        }

        List<FoundPost> finalList = new ArrayList<>();

        for (int i = 0; i < foundPosts.size(); i++) {
            FoundPost temp = foundPosts.get(i);
            boolean added = false;
            for (int j = 0; j < chars.size(); j++) {
                if (added) {
                    break;
                }
                if (temp.name.toLowerCase().contains(chars.get(j))) {
                    finalList.add(temp);
                    added = true;
                    break;
                }
                for (int z = 0; z < temp.getTags().indexOf(z); z++) {
                    if (temp.getTags().contains(chars.get(j))) {
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
    }
}
