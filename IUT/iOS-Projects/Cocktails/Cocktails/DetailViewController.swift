//
//  DetailViewController.swift
//  Cocktails
//
//  Created by iem on 05/03/2015.
//  Copyright (c) 2015 JeremyJacquemont. All rights reserved.
//

import UIKit

// MARK : - DetailViewControllerProtocol
@objc protocol DetailViewControllerProtocol {
    func reloadCocktail()
}

class DetailViewController: UIViewController, DetailViewControllerProtocol {
    
    // MARK: - Variables
    var cocktail: Cocktail?
    
    @IBOutlet weak var ingredientsLabel: UILabel!
    @IBOutlet weak var directivesLabel: UILabel!
    
    // MARK: - UIViewController Override
    override func viewDidLoad() {
        super.viewDidLoad()

        addButtons()
        setUpContent()
    }
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    // MARK: - Functions
    func addButtons(){
        let editBtn = UIBarButtonItem(barButtonSystemItem: UIBarButtonSystemItem.Edit, target: self, action: "editAction")
        self.navigationItem.rightBarButtonItems = [editBtn];
    }
    func setUpContent(){
        self.title = cocktail?.name
        
        self.ingredientsLabel.text = cocktail?.ingredients.stringByReplacingOccurrencesOfString(",", withString: "\n", options: nil, range: nil)
        self.ingredientsLabel.lineBreakMode = .ByWordWrapping
        self.ingredientsLabel.numberOfLines = 0
        self.ingredientsLabel.sizeToFit()
        
        self.directivesLabel.text = cocktail?.directions
        self.directivesLabel.lineBreakMode = .ByWordWrapping
        self.directivesLabel.numberOfLines = 0
        self.directivesLabel.sizeToFit()
    }
    func setCocktail(cocktail: Cocktail) {
        self.cocktail = cocktail;
    }
    func editAction(){
        let storyboard = UIStoryboard(name: "Main", bundle: nil)
        
        var editView: EditViewController = storyboard.instantiateViewControllerWithIdentifier("EditViewController") as EditViewController
        editView.cocktail = cocktail
        editView.type = EditViewControllerType.Edit
        editView.lastViewController = self
        
        let navigation = UINavigationController(rootViewController: editView)        
        self.presentViewController(navigation, animated: true, completion: nil)
    }    
    func reloadCocktail() {
        let manager = DBManager.sharedInstance
        let nameCocktail = self.cocktail?.name
        cocktail = manager.getCocktail(nameCocktail!)
        if let newCocktail = cocktail {
            self.cocktail = newCocktail
            setUpContent()
        }
    }
}
