//
//  PlaceViewController.swift
//  Locations
//
//  Created by Jérémy on 08/04/2015.
//  Copyright (c) 2015 JeremyJacquemont. All rights reserved.
//

import UIKit
import MapKit
import SwiftyJSON

class PlaceViewController: UIViewController, MKMapViewDelegate {
    //MARK: - Variables
    var location: Location?
    var type: String?
    var places: [GooglePlace]?
    
    var annotations = [CustomMKPointAnnotation]()
    
    @IBOutlet weak var mapView: MKMapView!

    //MARK: - Override Functions
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.mapView.delegate = self
        
        //Set Title
        self.title = type?.capitalizedString

        //Set All Markers
        //self.setUpMarkers()
        
        //SetUp Map
        if let location = location {
            let coord = CLLocationCoordinate2D(latitude: CLLocationDegrees(location.latitude), longitude: CLLocationDegrees(location.longitude))
            let region = MKCoordinateRegionMakeWithDistance(coord, 2000, 2000)
            mapView.regionThatFits(region)
            mapView.setRegion(region, animated: true)
        }
    }
    
    //MARK: - Functions
    func setUpMarkers() {
        mapView.removeAnnotations(mapView.annotations)
        annotations.removeAll(keepCapacity: true)
        
        if let places = self.places {
            for place in places {

                if let location = place.location {
                    var annotation = CustomMKPointAnnotation()
                    annotation.coordinate = location
                    annotation.title = place.name
                    annotation.visited = place.rating != nil
                    
                    mapView.addAnnotation(annotation)
                    annotations.append(annotation)
                }
                
            }
        }
    }
    func showDetail(place: GooglePlace) {
        let storyboard = UIStoryboard(name: "Main", bundle: nil)
        
        var destination: PlaceDetailViewController = storyboard.instantiateViewControllerWithIdentifier("PlaceDetail") as PlaceDetailViewController
        
        destination.place = place
        
        self.navigationController?.pushViewController(destination, animated: true)
    }
    
    //MARK: - MKMapViewDelegate
    func mapView(mapView: MKMapView!, viewForAnnotation annotation: MKAnnotation!) -> MKAnnotationView! {
            let identifier = "pin"
            var view: MKPinAnnotationView
            if let dequeuedView = mapView.dequeueReusableAnnotationViewWithIdentifier(identifier)
                as? MKPinAnnotationView {
                    dequeuedView.annotation = annotation
                    view = dequeuedView
            } else {
                view = MKPinAnnotationView(annotation: annotation, reuseIdentifier: identifier)
                view.canShowCallout = true
                view.calloutOffset = CGPoint(x: -5, y: 5)
                view.rightCalloutAccessoryView = UIButton.buttonWithType(.DetailDisclosure) as UIView
            }
        
            view.pinColor = (annotation as CustomMKPointAnnotation).getAnnotationColor()
            return view
    }
    
    func mapView(mapView: MKMapView!, annotationView view: MKAnnotationView!,
        calloutAccessoryControlTapped control: UIControl!) {
            let index = find(annotations, view.annotation as CustomMKPointAnnotation)!
            if let places = self.places {
                let place = places[index]
                showDetail(place)
            }
    }
    
}
