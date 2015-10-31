//
//  ViewController.swift
//  Cocktails
//
//  Created by iem on 12/02/2015.
//  Copyright (c) 2015 JeremyJacquemont. All rights reserved.
//

import UIKit

class MasterViewController: UITableViewController, UISearchBarDelegate, UISearchDisplayDelegate {

    // MARK: - Variables
    var cocktails: [Cocktail]!
    var filterCocktails = [Cocktail]()
    @IBOutlet weak var searchBarMaster: UISearchBar!
    
    var searchText = ""
    var scopeChoice = ""
    var isSearch = false
    
    // MARK: - UITableViewController Override
    override func viewDidLoad() {
        super.viewDidLoad()
        setupButtons()
        setupList()
        setupTable()
    }
    override func viewDidAppear(animated: Bool) {
        self.refresh(NSNull())
    }
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    // MARK: - Functions
    func setupButtons(){
        let addButton = UIBarButtonItem(barButtonSystemItem: UIBarButtonSystemItem.Add, target: self, action: "addAction")
        let searchButton = UIBarButtonItem(barButtonSystemItem: UIBarButtonSystemItem.Search, target: self, action: "searchAction")
        self.navigationItem.setRightBarButtonItems([addButton, searchButton], animated: true)
    }
    func addAction(){
        let storyboard = UIStoryboard(name: "Main", bundle: nil)
        
        var editView: EditViewController = storyboard.instantiateViewControllerWithIdentifier("EditViewController") as EditViewController
        editView.type = EditViewControllerType.Add
        editView.lastViewController = self
        
        let navigation = UINavigationController(rootViewController: editView)
        self.presentViewController(navigation, animated: true, completion: nil)
    }
    func searchAction() {
        searchBarMaster.becomeFirstResponder()
    }
    func setupList(){
        var manager = DBManager.sharedInstance
        self.cocktails = manager.getAllCocktails()
    }
    func setupTable(){
        var newBounds = self.tableView.bounds;
        newBounds.origin.y = newBounds.origin.y + searchBarMaster.bounds.size.height;
        self.tableView.bounds = newBounds;
        
        self.tableView.allowsMultipleSelectionDuringEditing = false
        self.refreshControl?.addTarget(self, action: "refresh:", forControlEvents: UIControlEvents.ValueChanged)
    }
    func refresh(sender:AnyObject)
    {
        setupList()
        self.tableView.reloadData()
        
        if isSearch {
            filterContentForSearchText(searchText, scope: scopeChoice)
            self.searchDisplayController?.searchResultsTableView.reloadData()
        }
        
        self.refreshControl?.endRefreshing()
    }
    
    // MARK: - Table view
    override func numberOfSectionsInTableView(tableView: UITableView) -> Int {
        return 1
    }
    override func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if tableView == self.searchDisplayController!.searchResultsTableView {
            return self.filterCocktails.count
        } else {
            return self.cocktails.count
        }
    }
    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = self.tableView.dequeueReusableCellWithIdentifier("CocktailCell") as UITableViewCell
        
        var cocktail : Cocktail
        if tableView == self.searchDisplayController!.searchResultsTableView {
            cocktail = filterCocktails[indexPath.row]
        } else {
            cocktail = cocktails[indexPath.row]
        }
        
        cell.textLabel!.text = cocktail.name
        cell.accessoryType = UITableViewCellAccessoryType.DisclosureIndicator
        return cell
    }
    override func tableView(tableView: UITableView, canEditRowAtIndexPath indexPath: NSIndexPath) -> Bool {
        return true
    }
    override func tableView(tableView: UITableView, commitEditingStyle editingStyle: UITableViewCellEditingStyle, forRowAtIndexPath indexPath: NSIndexPath) {
        if editingStyle == UITableViewCellEditingStyle.Delete {
            let manager = DBManager.sharedInstance
            
            if isSearch {
                var indexOpt = findIndexWithFilter(indexPath.row)
                if let index = indexOpt {
                    manager.deleteCocktail(index)
                    self.cocktails.removeAtIndex(index)
                    self.tableView.deleteRowsAtIndexPaths([NSIndexPath(forRow: index, inSection: indexPath.section)], withRowAnimation: UITableViewRowAnimation.Fade)
                    
                    self.filterCocktails.removeAtIndex(indexPath.row)
                    self.searchDisplayController?.searchResultsTableView.deleteRowsAtIndexPaths([indexPath], withRowAnimation: UITableViewRowAnimation.Fade)
                }
            }
            else {
                manager.deleteCocktail(indexPath.row)
                self.cocktails.removeAtIndex(indexPath.row)
                self.tableView.deleteRowsAtIndexPaths([indexPath], withRowAnimation: UITableViewRowAnimation.Fade)
            }
        }
    }
    func findIndexWithFilter(index: Int) -> Int?{
        let cocktailInFilter = self.filterCocktails[index]
        for (index, element) in enumerate(cocktails) {
            if let index = find(cocktails, cocktailInFilter) {
                return index
            }
        }
        return nil
    }
        
    // MARK: - Navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if segue.identifier == "DetailSegue"{
            var index : Int!
            var cocktail: Cocktail!
            
            if isSearch {
                index = self.searchDisplayController?.searchResultsTableView.indexPathForSelectedRow()?.item
                cocktail = self.filterCocktails[index]
            }
            else {
                index = tableView.indexPathForSelectedRow()?.item
                cocktail = self.cocktails[index]
            }
            
            var controller : DetailViewController = segue.destinationViewController as DetailViewController
            controller.setCocktail(cocktail)
        }
    }
    
    // MARK: - UISearchBarDelegate - UISearchDisplayDelegate
    func searchDisplayController(controller: UISearchDisplayController, didShowSearchResultsTableView tableView: UITableView) {
        isSearch = true
    }
    func searchDisplayController(controller: UISearchDisplayController, didHideSearchResultsTableView tableView: UITableView) {
        isSearch = false
    }
    
    func filterContentForSearchText(searchText: String, scope: String) {
        self.searchText = (searchText as NSString).lowercaseString
        self.scopeChoice = (scope as NSString).lowercaseString
        
        self.filterCocktails = self.cocktails.filter({( cocktail: Cocktail) -> Bool in
            let nameLowercase = (cocktail.name as NSString).lowercaseString
            var stringMatch : Range<String.Index>!
            switch (self.scopeChoice) {
                case "name":
                    stringMatch = (cocktail.name as NSString).lowercaseString.rangeOfString(self.searchText)
                    break;
                case "ingredients":
                    stringMatch = (cocktail.ingredients as NSString).lowercaseString.rangeOfString(self.searchText)
                    break;
                case "directives":
                    stringMatch = (cocktail.directions as NSString).lowercaseString.rangeOfString(self.searchText)
                    break;
                case "all":
                    stringMatch =
                        (cocktail.name as NSString).lowercaseString.rangeOfString(self.searchText) != nil ? (cocktail.name as NSString).lowercaseString.rangeOfString(self.searchText) :
                        (cocktail.ingredients as NSString).lowercaseString.rangeOfString(self.searchText) != nil ? (cocktail.ingredients as NSString).lowercaseString.rangeOfString(self.searchText) :
                        (cocktail.directions as NSString).lowercaseString.rangeOfString(self.searchText) != nil ? (cocktail.directions as NSString).lowercaseString.rangeOfString(self.searchText) : nil
                    break;
                default:
                    break;
            }
            
            return stringMatch != nil
        })
    }
    
    func searchDisplayController(controller: UISearchDisplayController!, shouldReloadTableForSearchString searchString: String!) -> Bool {
        let scopes = self.searchDisplayController?.searchBar.scopeButtonTitles as [String]
        let selectIndex = self.searchDisplayController?.searchBar.selectedScopeButtonIndex
        let selectedScope = scopes[selectIndex!] as String
        self.filterContentForSearchText(searchString, scope: selectedScope)
        return true
    }
    
    func searchDisplayController(controller: UISearchDisplayController!, shouldReloadTableForSearchScope searchOption: Int) -> Bool {
        let scope = self.searchDisplayController?.searchBar.scopeButtonTitles as [String]
        let selectedScope = scope[searchOption]
        self.filterContentForSearchText(self.searchDisplayController!.searchBar.text, scope: selectedScope)
        return true
    }

}