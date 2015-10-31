//
//  ShowViewController.swift
//  TP-BDD-1
//
//  Created by iem on 06/05/2015.
//  Copyright (c) 2015 JeremyJacquemont. All rights reserved.
//

import UIKit

class ShowViewController: UIViewController {
    //MARK: - Variables
    var task : Task?
    
    @IBOutlet weak var imageView: UIImageView!
    @IBOutlet weak var dateLabel: UILabel!
    @IBOutlet weak var categoryLabel: UILabel!
    @IBOutlet weak var textView: UITextView!
    
    
    //MARK: - Override Functions
    override func viewDidLoad() {
        super.viewDidLoad()
        
        //Update title
        self.title = task?.name
    }
    
    override func viewDidAppear(animated: Bool) {
        //Update Photo
        if let photo = task?.photo {
            imageView.image = UIImage(data: photo)
        }
        else {
            imageView.image = UIImage(named: "default-thumbnail")
        }
        
        //Update all texts
        if let date = task?.date {
            dateLabel.text = "\(date)"
        }
        if let category = task?.category {
            categoryLabel.text = category.name
        }
        if let description = task?.text {
            textView.text = description
        }
    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if segue.identifier == "showDetail" {
            let detailViewController = segue.destinationViewController as DetailViewController
            detailViewController.task = task
        }
    }
}
