//
//  PlaceDetailViewController.swift
//  Locations
//
//  Created by Jérémy on 08/04/2015.
//  Copyright (c) 2015 JeremyJacquemont. All rights reserved.
//

import UIKit
import SwiftyJSON

class PlaceDetailViewController: UIViewController, FloatRatingViewDelegate {
    
    //MARK: - Variables
    var loader: LoaderController?
    var place: GooglePlace?

    @IBOutlet weak var scrollView: UIScrollView!
    @IBOutlet weak var nameLabel: UILabel!
    @IBOutlet weak var descriptionLabel: UILabel!
    @IBOutlet weak var image: UIImageView!
    @IBOutlet weak var ratingLabel: UILabel!
    @IBOutlet weak var floatRating: FloatRatingView!
    @IBOutlet weak var cleanRating: UIButton!
    
    //MARK: - Override Functions
    override func viewDidLoad() {
        super.viewDidLoad()
        
        //Set Delegate
        self.floatRating.delegate = self
        
        //Show loader
        loader = LoaderController(view: self.view)
        
        //Get More informations
        if let currentPlace = place {
            loader?.showLoader()
            
            GooglePlacesHelper.sharedManager.getPlace(place, callback: { (error: NSError?, place: GooglePlace?) -> Void in
                if(error != nil) {
                    println("Error: \(error)")
                    self.loader?.hideLoader()
                }
                else {
                    if let place = place {
                        //Set informations
                        self.place = place
                        self.nameLabel.text = place.name
                        self.descriptionLabel.text = self.place?.description
                        self.image.image = place.image
                        
                        if let rating = place.rating {
                            self.floatRating.rating = Float(rating.note)
                        }
                        
                        //Update Size descriptionLabel
                        self.descriptionLabel.sizeToFit()
                        
                        //Update frames
                        self.updateFrames(self.descriptionLabel, otherViews: self.image, self.ratingLabel, self.floatRating, self.cleanRating)
                        
                        //Update ScrollView
                        var contentRect = CGRectZero
                        for view in self.scrollView.subviews {
                            contentRect = CGRectUnion(contentRect, view.frame)
                        }
                        self.scrollView.contentSize = CGSize(width: contentRect.size.width, height: contentRect.size.height)
                        
                        //Hide Loader
                        self.loader?.hideLoader()
                    }
                }
            })
        }

        self.title = "Detail"
    }
    
    //MARK: - Actions
    @IBAction func cleanRating(sender: AnyObject) {
        if let currentPlace = self.place {
            if let rating = currentPlace.rating {
                let manager = RatingManager.sharedManager
                manager.delete(rating)
                floatRating.rating = 0
            }
        }
    }
    
    //MARK: - Functions
    func updateFrames(defaultView: UIView, otherViews: UIView...) {
        for view in otherViews {
            view.frame = CGRect(
                x: view.frame.origin.x,
                y: view.frame.origin.y + defaultView.frame.height,
                width: view.frame.width,
                height: view.frame.height)
        }
    }
    
    //MARK: - FloatRatingViewDelegate
    func floatRatingView(ratingView: FloatRatingView, didUpdate rating: Float) {
        if let currentPlace = self.place {
            if let id = currentPlace.id {
                let manager = RatingManager.sharedManager
                var informations = Dictionary<String, AnyObject>()
                informations.updateValue(rating, forKey: Rating.FIELD_NOTE)
                informations.updateValue(id, forKey: Rating.FIELD_ID)

                if let currentRating = currentPlace.rating {
                    manager.update(currentRating, informations: informations)
                }
                else {
                    let newRating = manager.add(informations) as? Rating
                    if newRating != nil {
                        currentPlace.rating = newRating
                    }
                }
            }
        }
    }
}
