//
//  DetailViewController.swift
//  TP-BDD-1
//
//  Created by iem on 01/04/2015.
//  Copyright (c) 2015 JeremyJacquemont. All rights reserved.
//

import UIkit

class DetailViewController: UIViewController,
    UIActionSheetDelegate,
    UIImagePickerControllerDelegate,
    UINavigationControllerDelegate{

    //MARK: - Variables
    @IBOutlet weak var photoButton: UIButton!
    @IBOutlet weak var imageView: UIImageView!
    @IBOutlet weak var categoryButton: UIButton!
    @IBOutlet weak var dateButton: UIButton!
    @IBOutlet weak var descriptionButton: UIButton!
    
    var task : Task?
    var dataPhoto: NSData?
    
    //MARK: - Override Functions
    override func viewDidLoad() {
        self.title = task?.name
        
        //Set Image
        if let image = task?.photo {
            self.imageView.image = UIImage(data: image)
            photoButton.setTitle("Mettre à jour la photo", forState: UIControlState.Normal)
        }
        else {
            self.imageView.image = UIImage(named: "default-thumbnail")
        }
        
        //Set TapRecognizer
        let tapRecognizer = UITapGestureRecognizer(target: self, action: "showSheet:")
        tapRecognizer.numberOfTapsRequired = 1
        imageView.addGestureRecognizer(tapRecognizer)
        imageView.userInteractionEnabled = true
        
        //Set Button Items
        self.navigationItem.rightBarButtonItem = UIBarButtonItem(title: "Save", style: UIBarButtonItemStyle.Bordered, target: self, action: "saveImage")
        self.navigationItem.rightBarButtonItem?.enabled = false
    }
    
    override func viewDidAppear(animated: Bool) {
        //Set Buttons Title
        if let category = task?.category {
            categoryButton.setTitle("Mettre à jour la catégorie", forState: UIControlState.Normal)
        }
        
        if let date = task?.date {
            dateButton.setTitle("Mettre à jour la date ", forState: UIControlState.Normal)
        }
        
        if let description = task?.text {
            descriptionButton.setTitle("Mettre à jour la description", forState: UIControlState.Normal)
        }
    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if segue.identifier == "showCategory" {
            let categoryViewController = segue.destinationViewController as CategoryViewController
            categoryViewController.task = task!
        }
    }
    
    
    //MARK: - Actions
    @IBAction func showSheet(sender: AnyObject) {
        //Get title
        var title: String = String()
        if let photo = task?.photo {
            title = "Mettre à jour la photo"
        }
        else {
            title = "Ajouter une photo"
        }
        
        //Create Sheet
        let actionSheet = UIActionSheet(title: title, delegate: self, cancelButtonTitle: "Annuler", destructiveButtonTitle: nil)
        actionSheet.addButtonWithTitle("Depuis la caméra")
        actionSheet.addButtonWithTitle("Depuis la bibliothèque")
        
        //Show Sheet
        actionSheet.showInView(self.view)
    }
    
    @IBAction func showDatePicker(sender: AnyObject) {
        let storyboard = UIStoryboard(name: "Main", bundle: nil)
        let destinationController = storyboard.instantiateViewControllerWithIdentifier("editProperty") as EditPropertyViewController
        
        destinationController.task = task
        destinationController.type = EditPropertyType.Date
        
        let navigation = UINavigationController(rootViewController: destinationController)
        self.presentViewController(navigation, animated: true, completion: nil)
    }
    
    @IBAction func showDescription(sender: AnyObject) {
        let storyboard = UIStoryboard(name: "Main", bundle: nil)
        let destinationController = storyboard.instantiateViewControllerWithIdentifier("editProperty") as EditPropertyViewController
        
        destinationController.task = task
        destinationController.type = EditPropertyType.Description
        
        let navigation = UINavigationController(rootViewController: destinationController)
        self.presentViewController(navigation, animated: true, completion: nil)
    }
    
    
    //MARK: - Functions
    func saveImage() {
        //Get Data
        if let data = dataPhoto {
            //Set data & save
            task?.photo = data
            task?.managedObjectContext?.save(nil)
            
            //Update title
            photoButton.setTitle("Mettre à jour la photo", forState: UIControlState.Normal)
        }
        
        //Disable button
        self.navigationItem.rightBarButtonItem?.enabled = false
    }
    
    func showImageController(type: UIImagePickerControllerSourceType) {
        let imagePicker = UIImagePickerController()
        imagePicker.delegate = self
        imagePicker.sourceType = type
        imagePicker.allowsEditing = false
        self.presentViewController(imagePicker, animated: true, completion: nil)
    }
    
    
    //MARK: - UIActionSheetDelegate
    func actionSheet(actionSheet: UIActionSheet, clickedButtonAtIndex buttonIndex: Int) {
        switch buttonIndex {
            case 1:
                showImageController(UIImagePickerControllerSourceType.Camera)
                break
            case 2:
                showImageController(UIImagePickerControllerSourceType.PhotoLibrary)
                break
            default:
                break
        }
    }
    
    
    // MARK: - UIImagePickerControllerDelegate
    func imagePickerController(picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [NSObject : AnyObject]) {
        if let image: UIImage = info[UIImagePickerControllerOriginalImage] as? UIImage {
            self.imageView.image = image
            dataPhoto = UIImageJPEGRepresentation(image, 100)
            self.navigationItem.rightBarButtonItem?.enabled = true
        }
        self.dismissViewControllerAnimated(true, completion: nil)
    }
}