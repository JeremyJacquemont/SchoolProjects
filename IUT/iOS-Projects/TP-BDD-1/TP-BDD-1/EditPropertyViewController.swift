//
//  EditPropertyViewController.swift
//  TP-BDD-1
//
//  Created by Jérémy on 05/05/2015.
//  Copyright (c) 2015 JeremyJacquemont. All rights reserved.
//

import UIKit

//MARK: - EditPropertyType
enum EditPropertyType {
    case Date, Description
}

class EditPropertyViewController: UIViewController {
    //MARK: - Variables
    @IBOutlet weak var datePicker: UIDatePicker!
    @IBOutlet weak var textView: UITextView!

    var type : EditPropertyType?
    var task : Task?
    
    
    //MARK: - Override Functions
    override func viewDidLoad() {
        super.viewDidLoad()
        
        //Set Up interface
        setUp()
    }
    
    
    //MARK: - Functions
    func setUp() {
        //Set Cancel button
        self.navigationItem.leftBarButtonItem = UIBarButtonItem(barButtonSystemItem: UIBarButtonSystemItem.Cancel, target: self, action: "cancelAction")
        
        if let currentTask = task {
            if let currentType = type {
                //Set Save button
                self.navigationItem.rightBarButtonItem = UIBarButtonItem(barButtonSystemItem: UIBarButtonSystemItem.Save, target: self, action: "saveAction")
                
                //Activate interface
                switch currentType {
                case EditPropertyType.self.Date:
                    datePicker.hidden = false
                    if currentTask.date == nil {
                        self.title = "Ajout d'une date"
                    }
                    else {
                        datePicker.date = currentTask.date!
                        self.title = "Mise à jour de la date"
                    }
                    break;
                case EditPropertyType.self.Description:
                    textView.hidden = false
                    if currentTask.text == nil {
                        self.title = "Ajout d'une description"
                    }
                    else {
                        textView.text = currentTask.text!
                        self.title = "Mise à jour de la description"
                    }
                    break;
                default:
                    break;
                }
            }
        }
    }
    
    func saveAction() {
        switch type! {
        case EditPropertyType.self.Date:
            task!.date = datePicker.date
            task!.managedObjectContext?.save(nil)
            break;
        case EditPropertyType.self.Description:
            task!.text = textView.text
            task!.managedObjectContext?.save(nil)
            break;
        default:
            break;
        }
        self.dismissViewControllerAnimated(true, completion: nil)
    }
    
    func cancelAction() {
        self.dismissViewControllerAnimated(true, completion: nil)
    }
}
