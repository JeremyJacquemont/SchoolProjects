//
//  ViewController.swift
//  TP-BDD-1
//
//  Created by iem on 01/04/2015.
//  Copyright (c) 2015 JeremyJacquemont. All rights reserved.
//

import CoreData
import UIKit

class ViewController: UIViewController,
    UITableViewDataSource,
    UITableViewDelegate,
    UISearchBarDelegate,
    UISearchDisplayDelegate,
    NSFetchedResultsControllerDelegate,
    UIPopoverPresentationControllerDelegate {

    //MARK: - Constants
    let NUMBERS_OF_ELEMENTS = 10
    let KEY_DATA = "keyData"
    class var DEFAULT_SORT : NSSortDescriptor {return NSSortDescriptor(key: Task.FIELD_DATE, ascending: true) }
    
    //MARK: - Variables
    @IBOutlet weak var tableView: UITableView!
    @IBOutlet weak var searchBar: UISearchBar!
    @IBOutlet weak var sortNameButton: UIBarButtonItem!
    @IBOutlet weak var sortDateButton: UIBarButtonItem!
    @IBOutlet weak var sortCategoryButton: UIBarButtonItem!
    
    let taskManager = TaskManager.sharedManager
    var tasks = [Task]()
    
    lazy var fetchedResultsController: NSFetchedResultsController = {
        //Create NSFetchRequest
        let tasksFetchRequest = NSFetchRequest(entityName: Task.TABLE_NAME)
        
        //Set Descriptor
        tasksFetchRequest.sortDescriptors = [ViewController.DEFAULT_SORT]
        
        //Create NSFetchedResultsController
        let frc = NSFetchedResultsController(
            fetchRequest: tasksFetchRequest,
            managedObjectContext: DataManager.managedObjectContext,
            sectionNameKeyPath: Task.FIELD_DATE,
            cacheName: nil)
        
        //Set delegate
        frc.delegate = self
        
        //Return controller
        return frc
        }()
    
    //Filter
    var tasksFilter = [Task]()
    var searchText = ""
    var scopeChoice = ""
    var isSearch = false
    
    var sortNameBool: Bool? = nil
    var sortDateBool: Bool? = nil
    var sortCategoryBool: Bool? = nil
    
    //MARK: - Actions
    @IBAction func addTask(sender: UIBarButtonItem) {
        
        //Create alert
        let alert = UIAlertController(title: "ToDo", message: "Add Task", preferredStyle: UIAlertControllerStyle.Alert);
        
        //Add actions
        let saveAction = UIAlertAction(title: "Save", style: UIAlertActionStyle.Default, handler: { (_) -> Void in
         
            //Save data
            let content = alert.textFields![0] as UITextField
            if let task = self.taskManager.createTaskForName(content.text) {
                self.viewDidAppear(true)
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
    
    @IBAction func sortName(sender: AnyObject) {
        //Init value
        if sortNameBool == nil {
            sortNameBool == false
        }
        
        //Reset other values
        if sortDateBool != nil {
            sortDateBool = nil
            sortDateButton.title = "Date"
        }
        if sortCategoryBool != nil {
            sortCategoryBool = nil
            sortCategoryButton.title = "Category"
        }
        
        //Sort Descending
        if sortNameBool == true {
            sortNameButton.title = "Nom ↓"

            self.fetchedResultsController.fetchRequest.sortDescriptors? = [ViewController.DEFAULT_SORT, NSSortDescriptor(key: Task.FIELD_NAME, ascending: false)]
            self.fetchedResultsController.performFetch(nil)
            self.tableView.reloadData()
            
            sortNameBool = false
        }
            
        //Sort Ascending
        else {
            sortNameButton.title = "Nom ↑"
            
            self.fetchedResultsController.fetchRequest.sortDescriptors? = [ViewController.DEFAULT_SORT, NSSortDescriptor(key: Task.FIELD_NAME, ascending: true)]
            self.fetchedResultsController.performFetch(nil)
            self.tableView.reloadData()
            
            sortNameBool = true
        }
    }
    
    @IBAction func sortDate(sender: AnyObject) {
        //Init value
        if sortDateBool == nil {
            sortDateBool == false
        }
        
        //Reset other values
        if sortNameBool != nil {
            sortNameBool = nil
            sortNameButton.title = "Nom"
        }
        if sortCategoryBool != nil {
            sortCategoryBool = nil
            sortCategoryButton.title = "Category"
        }
        
        //Sort Descending
        if sortDateBool == true {
            sortDateButton.title = "Date ↓"
            
            self.fetchedResultsController.fetchRequest.sortDescriptors = [ViewController.DEFAULT_SORT,NSSortDescriptor(key: Task.FIELD_DATE, ascending: false)]
            self.fetchedResultsController.performFetch(nil)
            self.tableView.reloadData()
            
            sortDateBool = false
        }
        
        //Sort Ascending
        else {
            sortDateButton.title = "Date ↑"
            
            self.fetchedResultsController.fetchRequest.sortDescriptors = [ViewController.DEFAULT_SORT,NSSortDescriptor(key: Task.FIELD_DATE, ascending: true)]
            self.fetchedResultsController.performFetch(nil)
            self.tableView.reloadData()
            
            sortDateBool = true
        }
    }
    
    @IBAction func sortCategory(sender: AnyObject) {
        //Init value
        if sortCategoryBool == nil {
            sortCategoryBool == false
        }
        
        //Reset other values
        if sortNameBool != nil {
            sortNameBool = nil
            sortNameButton.title = "Nom"
        }
        if sortDateBool != nil {
            sortDateBool = nil
            sortDateButton.title = "Date"
        }
        
        //Sort Descending
        if sortCategoryBool == true {
            sortCategoryButton.title = "Category ↓"
            
            self.fetchedResultsController.fetchRequest.sortDescriptors = [ViewController.DEFAULT_SORT,NSSortDescriptor(key: "\(Task.FIELD_CATEGORY).\(Category.FIELD_NAME)", ascending: false)]
            self.fetchedResultsController.performFetch(nil)
            self.tableView.reloadData()
            
            sortCategoryBool = false
        }
            
        //Sort Ascending
        else {
            sortCategoryButton.title = "Category ↑"
            
            self.fetchedResultsController.fetchRequest.sortDescriptors = [ViewController.DEFAULT_SORT, NSSortDescriptor(key: "\(Task.FIELD_CATEGORY).\(Category.FIELD_NAME)", ascending: true)]
            self.fetchedResultsController.performFetch(nil)
            self.tableView.reloadData()
            
            sortCategoryBool = true
        }
    }
    
    @IBAction func groupByChoice(sender: AnyObject) {
        let storyboard : UIStoryboard = UIStoryboard(name: "Main", bundle: nil)
        var popoverViewController = storyboard.instantiateViewControllerWithIdentifier("PopoverViewController") as PopoverViewController
        popoverViewController.modalPresentationStyle = .Popover
        popoverViewController.preferredContentSize = CGSizeMake(160, 250)
        popoverViewController.titles = ["Name", "Category", "Date"]
        
        let popoverMenuViewController = popoverViewController.popoverPresentationController
        popoverMenuViewController?.permittedArrowDirections = .Any
        popoverMenuViewController?.delegate = self
        popoverMenuViewController?.barButtonItem = sender as UIBarButtonItem
            
        self.presentViewController(popoverViewController, animated: true, completion: nil)
    }
    
    //Unsused
    /*@IBAction func reorderAction(sender: AnyObject) {
        self.tableView.editing = !self.tableView.editing
    }*/
    
    //MARK: - Override Functions
    override func viewDidLoad() {
        super.viewDidLoad()

        //Get Results
        var error: NSError? = nil
        if (fetchedResultsController.performFetch(&error) == false) {
            print("An error occurred: \(error?.localizedDescription)")
        }
        
        //Add search button
        let searchButton = UIBarButtonItem(barButtonSystemItem: UIBarButtonSystemItem.Search, target: self, action: "searchAction")
        self.navigationItem.rightBarButtonItems?.append(searchButton)
        
        //SetUp table
        var newBounds = self.tableView.bounds;
        newBounds.origin.y = newBounds.origin.y + searchBar.bounds.size.height;
        self.tableView.bounds = newBounds;
        self.tableView.allowsMultipleSelectionDuringEditing = false
    }
    
    override func viewDidAppear(animated: Bool) {
        self.tableView.reloadData()
    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if segue.identifier == "showInformations" {
            let showController = segue.destinationViewController as ShowViewController
            
            var index : Int!
            var task: Task?
            
            if isSearch {
                index = self.searchDisplayController?.searchResultsTableView.indexPathForSelectedRow()?.item
                task = self.tasksFilter[index]
            }
            else {
                task = self.fetchedResultsController.objectAtIndexPath(self.tableView.indexPathForSelectedRow()!) as? Task
            }
            
            showController.task = task
        }
    }
    
    //MARK: - Functions
    func searchAction() {
        searchBar.becomeFirstResponder()
    }
    
    func filterContentForSearchText(searchText: String, scope: String) {
        //To LowerCase all elements
        self.searchText = (searchText as NSString).lowercaseString
        self.scopeChoice = (scope as NSString).lowercaseString
        
        //Execute good request
        switch (self.scopeChoice) {
        case "name":
            if let result = taskManager.searchTasksWithName(searchText) {
                self.tasksFilter = result
            }
            break;
        case "category":
            if let result = taskManager.searchTasksWithCategory(searchText) {
                self.tasksFilter = result
            }
            break;
        default:
            break;
        }
    }
    
    func findIndexWithFilter(index: Int) -> Int?{
        let taskInFilter = self.tasksFilter[index]
        for (index, element) in enumerate(tasks) {
            if let index = find(tasks, taskInFilter) {
                return index
            }
        }
        return nil
    }
    
    func setUpCell(cell: UITableViewCell, atIndexPath indexPath: NSIndexPath, inTableView tableView: UITableView) {
        var task: Task?
        
        if tableView == self.searchDisplayController!.searchResultsTableView {
            task = self.tasksFilter[indexPath.row]
        }
        else {
            task = self.fetchedResultsController.objectAtIndexPath(indexPath) as? Task
        }
        
        cell.textLabel?.text = task?.name
        
        if let photo = task?.photo {
            cell.imageView?.image = UIImage(data: photo)
        }
        else {
            cell.imageView?.image = UIImage(named: "default-thumbnail")
        }
        
        if let category = task?.category {
            cell.detailTextLabel?.text = category.name
        }
        else {
            cell.detailTextLabel?.text = ""
        }
    }
    
    
    //MARK: - UIPopoverPresentationControllerDelegate
    func adaptivePresentationStyleForPresentationController(controller: UIPresentationController!) -> UIModalPresentationStyle {
        return .None
    }
    
    
    //MARK: - UITableViewDelegate
    func tableView(tableView: UITableView, editActionsForRowAtIndexPath indexPath: NSIndexPath) -> [AnyObject]? {
        //Add Delete button
        let deleteAction = UITableViewRowAction(style: .Default, title: "Delete") { (rowAction, indexPath) -> Void in
            self.tableView(tableView, commitEditingStyle: .Delete, forRowAtIndexPath: indexPath)
        }
        
        return [deleteAction];
    }
    
    func tableView(tableView: UITableView!, commitEditingStyle editingStyle: UITableViewCellEditingStyle, forRowAtIndexPath indexPath: NSIndexPath!) {
        //Handler execute on click "Delete"
        if (editingStyle == UITableViewCellEditingStyle.Delete) {
            var entityOpt: Task?
            if isSearch {
                var indexOpt = findIndexWithFilter(indexPath.row)
                if let index = indexOpt {
                    entityOpt = self.tasksFilter[index]
                }
            }
            else {
                entityOpt = self.fetchedResultsController.objectAtIndexPath(indexPath) as? Task
            }
            
            //Delete entity
            if let entity = entityOpt {
                if (self.taskManager.deleteTask(entity) != nil) {
                    self.tableView.reloadData()
                }
            }
        }
    }
    
    
    //MARK: - UITableViewDataSource
    func numberOfSectionsInTableView(tableView: UITableView) -> Int {
        if tableView == self.searchDisplayController!.searchResultsTableView {
            return 1
        }
        else {
            if let sections = fetchedResultsController.sections {
                return sections.count
            }
            
            return 0
        }
    }
    
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if tableView == self.searchDisplayController!.searchResultsTableView {
            return self.tasksFilter.count
        }
        else {
            if let sections = fetchedResultsController.sections {
                let currentSection = sections[section] as NSFetchedResultsSectionInfo
                return currentSection.numberOfObjects
            }
            return 0
        }
    }
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = UITableViewCell(style: UITableViewCellStyle.Subtitle, reuseIdentifier: "Cell") as UITableViewCell
        
        return cell
    }
    
    func tableView(tableView: UITableView, willDisplayCell cell: UITableViewCell, forRowAtIndexPath indexPath: NSIndexPath) {
        self.setUpCell(cell, atIndexPath: indexPath, inTableView: tableView)
    }
    
    func tableView(tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
        if tableView == self.searchDisplayController!.searchResultsTableView {
            return nil
        }
        else {
            if let sections = fetchedResultsController.sections {
                let currentSection = sections[section] as NSFetchedResultsSectionInfo
                return currentSection.name
            }
            
            return nil
        }
    }
    
    func tableView(tableView: UITableView, moveRowAtIndexPath sourceIndexPath: NSIndexPath, toIndexPath destinationIndexPath: NSIndexPath) {
        self.controller(self.fetchedResultsController, didChangeObject:NSNull(), atIndexPath: sourceIndexPath, forChangeType: NSFetchedResultsChangeType.Move, newIndexPath: destinationIndexPath)
    }
    
    func tableView(tableView: UITableView, canMoveRowAtIndexPath indexPath: NSIndexPath) -> Bool {
        return true
    }
    
    func tableView(tableView: UITableView, canEditRowAtIndexPath indexPath: NSIndexPath) -> Bool {
        return true
    }
    
    
    // MARK: - UISearchBarDelegate - UISearchDisplayDelegate
    func searchDisplayController(controller: UISearchDisplayController, didShowSearchResultsTableView tableView: UITableView) {
        isSearch = true
    }
    
    func searchDisplayController(controller: UISearchDisplayController, didHideSearchResultsTableView tableView: UITableView) {
        isSearch = false
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
    
    
    //MARK: - NSFetchedResultsControllerDelegate
    func controllerWillChangeContent(controller: NSFetchedResultsController) {
        self.tableView.beginUpdates()
    }
    
    func controller(controller: NSFetchedResultsController, didChangeObject anObject: AnyObject, atIndexPath indexPath: NSIndexPath, forChangeType type: NSFetchedResultsChangeType,  newIndexPath: NSIndexPath) {
            switch type {
            case .Insert:
                self.tableView.insertRowsAtIndexPaths([newIndexPath], withRowAnimation: .Fade)
            case .Update:
                let cell = self.tableView.cellForRowAtIndexPath(indexPath)
                self.setUpCell(cell!, atIndexPath: indexPath, inTableView: self.tableView)
                self.tableView.reloadRowsAtIndexPaths([indexPath], withRowAnimation: .Fade)
            case .Move:
                var tmp = self.fetchedResultsController.objectAtIndexPath(indexPath) as Task
                var values = self.fetchedResultsController.fetchedObjects as [Task]
                
                values[newIndexPath.row] = self.fetchedResultsController.objectAtIndexPath(indexPath) as Task
                values[indexPath.row] = tmp
            case .Delete:
                self.tableView.deleteRowsAtIndexPaths([indexPath], withRowAnimation: .Fade)
            default:
                return
            }
    }
    
    func controller(controller: NSFetchedResultsController, didChangeSection sectionInfo: NSFetchedResultsSectionInfo, atIndex sectionIndex: Int, forChangeType type: NSFetchedResultsChangeType) {
        switch type {
        case .Insert :
            self.tableView.insertSections(NSIndexSet(index: sectionIndex), withRowAnimation: UITableViewRowAnimation.Fade)
            break;
        case .Delete :
            self.tableView.deleteSections(NSIndexSet(index: sectionIndex), withRowAnimation: UITableViewRowAnimation.Fade)
            break;
        default:
            break;
        }
    }
    
    func controllerDidChangeContent(controller: NSFetchedResultsController) {
        self.tableView.endUpdates()
    }
}

