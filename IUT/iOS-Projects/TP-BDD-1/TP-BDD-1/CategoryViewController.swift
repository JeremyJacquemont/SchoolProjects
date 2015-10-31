//
//  CategoryViewController.swift
//  TP-BDD-1
//
//  Created by iem on 08/04/2015.
//  Copyright (c) 2015 JeremyJacquemont. All rights reserved.
//

import UIkit

class CategoryViewController: UITableViewController {
    //MARK: - Variables
    var task: Task?
    
    var categoryManager = CategoryManager.sharedManager
    var categories = [Category]()
    var selectedIndex: NSIndexPath? = nil
    
    
    //MARK: - Actions
    @IBAction func addCategory(sender: UIBarButtonItem) {
        //Create alert
        let alert = UIAlertController(title: "Category", message: "Add Category", preferredStyle: UIAlertControllerStyle.Alert);
        
        //Add actions
        let saveAction = UIAlertAction(title: "Save", style: UIAlertActionStyle.Default, handler: { (_) -> Void in
            
            //Save data
            let content = alert.textFields![0] as UITextField
            if let task = self.categoryManager.createCategoryForName(content.text) {
                self.categories.append(task)
                self.tableView.reloadSections(NSIndexSet(index: 0), withRowAnimation: UITableViewRowAnimation.Fade)
            }
        })
        let cancelAction = UIAlertAction(title: "Cancel", style: UIAlertActionStyle.Cancel, handler: nil)
        
        //Add textfield
        alert.addTextFieldWithConfigurationHandler(nil)
        
        //Set actions
        alert.addAction(saveAction);
        alert.addAction(cancelAction);
        
        //Show alert
        presentViewController(alert, animated: true, completion: nil)
    }
    
    
    //MARK: - Override Functions
    override func viewDidLoad() {
        super.viewDidLoad()
        
        //Set Title
        self.title = "Category"
        
        //Get data
        if let data = self.categoryManager.fetchCategories() {
            categories = data
        }
        
        //Add add button
        let addButton = UIBarButtonItem(barButtonSystemItem: UIBarButtonSystemItem.Add, target: self, action: "addCategory:")
        self.navigationItem.rightBarButtonItem = addButton
    }
    
    override func viewDidAppear(animated: Bool) {
        self.tableView.reloadData()
    }
    
    
    //MARK: - UITableViewDelegate
    override func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.categories.count
    }
    
    override func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
        //Reset selected
        if let previousIndexPath: NSIndexPath = selectedIndex {
            let cell = tableView.cellForRowAtIndexPath(previousIndexPath)
            cell?.accessoryType = UITableViewCellAccessoryType.None
            tableView.deselectRowAtIndexPath(indexPath, animated: true)
        }
        
        //Selected
        let cell = tableView.cellForRowAtIndexPath(indexPath)
        cell?.accessoryType = UITableViewCellAccessoryType.Checkmark
        selectedIndex = indexPath
        tableView.deselectRowAtIndexPath(indexPath, animated: true)
        
        //Save category
        var category = self.categories[indexPath.row]
        task!.category = category
        DataManager.saveContext()
    }
    
    
    //MARK: - UITableViewDataSource    
    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = self.tableView.dequeueReusableCellWithIdentifier("CellCategory") as UITableViewCell
        var category : Category = self.categories[indexPath.row]
        cell.textLabel?.text = category.name
        
        //Set Selected element
        if let selectTask = task {
            let selectCategory = selectTask.category
            if selectCategory == category {
                cell.accessoryType = UITableViewCellAccessoryType.Checkmark
                selectedIndex = indexPath
                tableView.deselectRowAtIndexPath(indexPath, animated: true)
            }
        }
        
        return cell
    }
    
    override func tableView(tableView: UITableView, canEditRowAtIndexPath indexPath: NSIndexPath) -> Bool {
        return true
    }
    
    override func tableView(tableView: UITableView, commitEditingStyle editingStyle: UITableViewCellEditingStyle, forRowAtIndexPath indexPath: NSIndexPath) {
        //Handler execute on click "Delete"
        if (editingStyle == UITableViewCellEditingStyle.Delete) {
            let entity = self.categories[indexPath.row]
            categoryManager.deleteCategory(entity)
            
            self.categories.removeAtIndex(indexPath.row)
            self.tableView.deleteRowsAtIndexPaths([indexPath], withRowAnimation: UITableViewRowAnimation.Automatic)
        }
    }
}
