//
//  PlaceToBeViewController.swift
//  Locations
//
//  Created by Jérémy on 08/04/2015.
//  Copyright (c) 2015 JeremyJacquemont. All rights reserved.
//

import UIKit

class PlaceToBeViewController: UITableViewController {
    //MARK: - Variables
    var choices = [String: [GooglePlace]]()
    var location: Location?
    var loader: LoaderController?
    
    //MARK: - Override functions
    override func viewDidAppear(animated: Bool) {
        //SetUp Loader
        loader = LoaderController(view: self.view)
        
        //Update Data
        if let currentLocation = location {
            loader?.showLoader()
            
            GooglePlacesHelper.sharedManager.searchPlaces(currentLocation, callback: { (error: NSError?, places: [GooglePlace]?) -> Void in
                if(error != nil) {
                    println("Error: \(error)")
                    self.loader?.hideLoader()
                }
                else {
                    if let places = places {
                        if places.count > 0 {
                            self.choices = self.filterResults(places)
                        }
                        self.tableView.reloadData()
                        self.loader?.hideLoader()
                    }
                }
            })
        }
    }
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if segue.identifier == "showPlace" {
            var destinationController = segue.destinationViewController as PlaceViewController
            
            if(choices.count > 0) {
                let type = GooglePlacesHelper.TYPES[self.tableView.indexPathForSelectedRow()!.row]
                
                destinationController.location = location
                destinationController.type = type
                destinationController.places = choices[type]
            }
        }
    }
    
    //MARK: - Functions
    func filterResults(results: [GooglePlace]) -> [String: [GooglePlace]]{
        var resultValue = [String: [GooglePlace]]()
        
        for type in GooglePlacesHelper.TYPES {
            resultValue[type] = [GooglePlace]()
            for result in results {
                if let types = result.types {
                    if contains(types, type) {
                        resultValue[type]?.append(result)
                    }
                }
            }
            
            if resultValue[type]?.count == 0 {
               resultValue.removeValueForKey(type)
            }
        }
        
        return resultValue
    }
    
    //MARK: - UITableViewDelegate
    override func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.choices.count
    }
    
    //MARK: - UITableViewDataSource
    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let type = GooglePlacesHelper.TYPES[indexPath.row]
        if  let choice = self.choices[type] {
            //Get Variables
            let cell = self.tableView.dequeueReusableCellWithIdentifier("CellPlaceToBe") as PlaceTableViewCell
        
            //Set Text
            cell.label.text = type.capitalizedString
        
            //Set Image
            cell.imageRateView.setImage(UIImage(named: type)!)
        
            //Set Rate
            var total = 0.0;
            for place in choice {
                if place.rating != nil {
                    total = total + 1
                }
            }
            
            let perc = total / Double(choice.count)
            cell.imageRateView.setRating(CGFloat(perc))
        
            return cell
        }
        return UITableViewCell()
    }
    
}
