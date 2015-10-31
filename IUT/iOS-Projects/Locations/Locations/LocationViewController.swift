//
//  LocationViewController.swift
//  Locations
//
//  Created by iem on 02/04/2015.
//  Copyright (c) 2015 JeremyJacquemont. All rights reserved.
//

import UIKit
import MapKit
import AddressBook

class LocationViewController: UIViewController, MKMapViewDelegate {
    
    //MARK : - Variables
    @IBOutlet weak var mapView: MKMapView!
    @IBOutlet weak var slider: UISlider!
    var value: String?
    
    var annotation: MKPointAnnotation?
    var radius: CLLocationDistance = 250
    var geocoder: CLGeocoder?
    
    var location: Location?
    var loader: LoaderController?
    
    //MARK: - Override Functions
    override func viewDidLoad() {
        super.viewDidLoad()
        
        setUpNavigation()
        
        //Init loader
        loader = LoaderController(view: self.view)
        
        //SetUp map
        mapView.delegate = self
        mapView.showsUserLocation = true        
        
        //Add gesture
        let tapMap = UITapGestureRecognizer(target: self, action: "getPosition:")
        tapMap.numberOfTapsRequired = 1
        tapMap.numberOfTouchesRequired = 1
        mapView.addGestureRecognizer(tapMap)
        
        if location != nil {
            updateForLocation()
        }
    }
    
    //MARK: - Actions
    @IBAction func getPosition(recognizer: UITapGestureRecognizer) {
        let point = recognizer.locationInView(self.mapView)
        let coord = self.mapView.convertPoint(point, toCoordinateFromView: self.mapView)
        
        updateAnnotation(coord, enable: true)
    }
    @IBAction func sliderChangedValue(sender: AnyObject) {
        if let annotationElement = annotation {
            radius = Double(self.slider.value)
            addCircleAnnotation(annotationElement.coordinate)
            
            if annotation != nil {
                self.navigationItem.rightBarButtonItem?.enabled = true
            }
        }
    }
    
    //MARK: - Functions
    func updateForLocation() {
        //Create Region
        let coord = CLLocationCoordinate2D(latitude: Double(location!.latitude), longitude: Double(location!.longitude))
        let region = MKCoordinateRegionMakeWithDistance(coord, 2000, 2000)
        mapView.regionThatFits(region)
        mapView.setRegion(region, animated: true)
        
        //Update Radius
        radius = Double(location!.radius)
        slider.value = Float(radius)
        
        //Update Annotation
        updateAnnotation(coord, enable: false)
    }
    func updateAnnotation(coord: CLLocationCoordinate2D, enable: Bool) {
        if enable == true {
            //Enable save button
            self.navigationItem.rightBarButtonItem?.enabled = true
        }
        
        //Update annotation
        if let annotationElement = annotation {
            mapView.removeAnnotation(annotation)
            annotation?.coordinate = coord
            self.mapView.addAnnotation(annotation)
        }
        else {
            annotation = MKPointAnnotation()
            annotation?.coordinate = coord
            annotation?.title = "Location"
            annotation?.subtitle = "Your selected location"
            self.mapView.addAnnotation(annotation)
            
            //Update Region
            let coord = CLLocationCoordinate2D(latitude: coord.latitude, longitude: coord.longitude)
            let region = MKCoordinateRegionMakeWithDistance(coord, 2000, 2000)
            mapView.regionThatFits(region)
            mapView.setRegion(region, animated: true)
        }
        
        //Draw circle with start Action Slider
        sliderChangedValue(NSNull())
    }
    
    func addCircleAnnotation(coord: CLLocationCoordinate2D) {
        mapView.removeOverlays(mapView.overlays)
        let circle = MKCircle(centerCoordinate: coord, radius: radius)
        mapView.addOverlay(circle)
    }
    func setUpNavigation() {
        self.title = "Pick Location"
        
        //Create Navigation Buttons
        let buttonCancel = UIBarButtonItem(barButtonSystemItem: UIBarButtonSystemItem.Cancel, target: self, action: "cancel")
        let buttonSave = UIBarButtonItem(barButtonSystemItem: UIBarButtonSystemItem.Save, target: self, action: "saveLocation")
        buttonSave.enabled = false
        
        //Set Navigation Buttons
        self.navigationItem.setLeftBarButtonItem(buttonCancel, animated: true)
        self.navigationItem.setRightBarButtonItem(buttonSave, animated: true)
    }
    func cancel() {
        dismissViewControllerAnimated(true, completion: nil)
    }
    func saveLocation() {
        //Set Titles
        var titleAlert = String()
        var titleButton = String()
        if location == nil {
            titleAlert = "Add a Location"
            titleButton = "Save"
        }
        else {
            titleAlert = "Update a Location"
            titleButton = "Update"
        }
        
        //Create Alert & Show
        let alert = UIAlertController(title: titleAlert, message: "Add a name for Location", preferredStyle: UIAlertControllerStyle.Alert)
        alert.addTextFieldWithConfigurationHandler(nil)
        alert.addAction(UIAlertAction(title: "Cancel", style: UIAlertActionStyle.Cancel, handler: nil))
        alert.addAction(UIAlertAction(title: titleButton, style: UIAlertActionStyle.Default, handler: { (_) -> Void in
            
            let manager = LocationManager()
            let informations = self.createInformations((alert.textFields![0] as UITextField).text, longitude: self.annotation!.coordinate.longitude, latitue: self.annotation!.coordinate.latitude, radius: self.radius)
            if self.location != nil {
                if let location = manager.update(self.location, informations: informations) {
                    self.dismissViewControllerAnimated(true, completion: nil)
                }
                else{
                    println("An error occured during update a location")
                }
            }
            else {
                if let location = manager.add(informations){
                    self.dismissViewControllerAnimated(true, completion: nil)
                }
                else{
                    println("An error occured during save new location")
                }
            }
            
        }))
        
        if location == nil {
            //Check geoCoder
            if geocoder == nil {
                geocoder = CLGeocoder()
            }
        
            loader?.showLoader()
        
            //Get informations
            geocoder?.reverseGeocodeLocation(
                CLLocation(latitude: annotation!.coordinate.latitude, longitude: annotation!.coordinate.longitude),
                completionHandler: { (placemarks: [AnyObject]!, error: NSError!) -> Void in
                    if let placemarksLists = placemarks {
                        let placemark = placemarksLists[0] as? CLPlacemark
                        if let placemarkValue = placemark {
                            if placemarkValue.areasOfInterest != nil && placemarkValue.areasOfInterest.count > 0 {
                                let area = placemarkValue.areasOfInterest[0] as String
                                (alert.textFields![0] as UITextField).text = area
                            }
                            else {
                                let value = placemarkValue.addressDictionary[kABPersonAddressCityKey] as String
                                (alert.textFields![0] as UITextField).text = value
                            }
                        }
                    }
                
                    self.loader?.hideLoader()
                
                    //Show view
                    self.presentViewController(alert, animated: true, completion: nil)
            })
        }
        else {
            (alert.textFields![0] as UITextField).text = location!.name
            self.presentViewController(alert, animated: true, completion: nil)
        }
    }
    func createInformations(name: String, longitude: NSNumber, latitue: NSNumber, radius: NSNumber) -> Dictionary<String, AnyObject> {
        var dict = Dictionary<String, AnyObject>()
        dict[Location.FIELD_NAME] = name
        dict[Location.FIELD_LONGITUDE] = longitude
        dict[Location.FIELD_LATITUDE] = latitue
        dict[Location.FIELD_RADIUS] = radius
        return dict
    }
    
    //MARK: - MKMapKit Delegate
    func mapView(mapView: MKMapView!, didUpdateUserLocation userLocation: MKUserLocation!) {
        if location == nil {
            let region = MKCoordinateRegionMakeWithDistance(userLocation.location.coordinate, 2000, 2000)
            mapView.regionThatFits(region)
            mapView.setRegion(region, animated: true)
        }
    }    
    func mapView(mapView: MKMapView!, rendererForOverlay overlay: MKOverlay!) -> MKOverlayRenderer! {
        let circle : MKCircle = overlay as MKCircle
        let circleViewRenderer: MKCircleRenderer = MKCircleRenderer(circle: circle)
        
        circleViewRenderer.fillColor = UIColor.whiteColor()
        circleViewRenderer.strokeColor = UIColor.greenColor()
        circleViewRenderer.lineWidth = 1
        
        return circleViewRenderer
    }
}
