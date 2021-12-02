package movieTicketSystem.controller;

import movieTicketSystem.View.MovieSelectionView;

public class ViewController {

    public static void main(String[] args) {
        MovieSelectionView movieSelectionView = new MovieSelectionView();
        MovieSelectionViewController movieSelectionViewController = new MovieSelectionViewController(movieSelectionView);
    }

}
