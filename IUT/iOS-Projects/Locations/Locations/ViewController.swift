//
//  ViewController.swift
//  Locations
//
//  Created by iem on 02/04/2015.
//  Copyright (c) 2015 JeremyJacquemont. All rights reserved.
//

import UIKit

class ViewController: UITableViewController {
    //MARK: - Variables
    var locations = [Location]()
    var locationManager = LocationManager.sharedManager
    
    //MARK: - Override Functions
    override func viewDidLoad() {
        super.viewDidLoad()
        setUpNavigation()
    }
    override func viewDidAppear(animated: Bool) {
        if let locationsResult = locationManager.getAll() {
            locations = locationsResult as [Location]
            self.tableView.reloadData()
        }
    }
    
    //MARK: - Functions
    func setUpNavigation() {
        self.navigationItem.rightBarButtonItem = UIBarButtonItem(barButtonSystemItem: UIBarButtonSystemItem.Add, target: self, action: "showAdd")
        self.refreshControl?.addTarget(self, action: "pullToRefresh", forControlEvents: UIControlEvents.ValueChanged)
    }
    func showAdd() {        
        let storyboard = UIStoryboard(name: "Main", bundle: nil)
        
        var destination: LocationViewController = storyboard.instantiateViewControllerWithIdentifier("Location") as LocationViewController
        
        let navigation = UINavigationController(rootViewController: destination)
        self.presentViewController(navigation, animated: true, completion: nil)
    }
    func pullToRefresh() {
        viewDidAppear(false)
        self.refreshControl?.endRefreshing()
    }
    
    
    //MARK: - UITableViewDelegate
    override func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.locations.count
    }
    override func tableView(tableView: UITableView, editActionsForRowAtIndexPath indexPath: NSIndexPath) -> [AnyObject]? {
        
        //Add Edit button
        let editAction = UITableViewRowAction(style: .Default, title: "Edit") { (rowAction, indexPath) -> Void in
            let storyboard = UIStoryboard(name: "Main", bundle: nil)
            
            var destination: LocationViewController = storyboard.instantiateViewControllerWithIdentifier("Location") as LocationViewController
            destination.location = self.locations[indexPath.row]
            
            let navigation = UINavigationController(rootViewController: destination)
            self.presentViewController(navigation, animated: true, completion: nil)
        }
        editAction.backgroundColor = UIColor.purpleColor()
        
        //Add Delete button
        let deleteAction = UITableViewRowAction(style: .Default, title: "Delete") { (rowAction, indexPath) -> Void in
            
            var alert = UIAlertController(title: "Delete Location", message: "Please, confirm delete!", preferredStyle: UIAlertControllerStyle.Alert)
            
            var cancelBtn = UIAlertAction(title: "Cancel", style: UIAlertActionStyle.Cancel, handler: { (_) -> Void in
                alert.dismissViewControllerAnimated(true, completion: nil)
            })
            var okBtn = UIAlertAction(title: "Delete", style: UIAlertActionStyle.Default, handler: { (_) -> Void in
                self.tableView(tableView, commitEditingStyle: .Delete, forRowAtIndexPath: indexPath)

            })
            
            alert.addAction(cancelBtn)
            alert.addAction(okBtn)
            
            self.presentViewController(alert, animated: true, completion: nil)
        }
        
        return [deleteAction, editAction];
    }
    override func tableView(tableView: UITableView, commitEditingStyle editingStyle: UITableViewCellEditingStyle, forRowAtIndexPath indexPath: NSIndexPath) {
        //Handler execute on click "Delete"
        if (editingStyle == UITableViewCellEditingStyle.Delete) {
            let entity = self.locations[indexPath.row]
            locationManager.delete(entity)
            
            self.locations.removeAtIndex(indexPath.row)
            self.tableView.deleteRowsAtIndexPaths([indexPath], withRowAnimation: UITableViewRowAnimation.Automatic)
        }
    }
    
    //MARK: - UITableViewDataSource
    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = self.tableView.dequeueReusableCellWithIdentifier("Cell") as UITableViewCell
        cell.textLabel?.text = self.locations[indexPath.row].name
        return cell
    }
    
    override func tableView(tableView: UITableView, canEditRowAtIndexPath indexPath: NSIndexPath) -> Bool {
        return true
    }
    
    // MARK: - Navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if segue.identifier == "placeToBe" {
            let indexPath = self.tableView.indexPathForSelectedRow()
            if let index = indexPath {
                let value = self.locations[index.row]
                (segue.destinationViewController as PlaceToBeViewController).location = value
            }
        }
    }

}

