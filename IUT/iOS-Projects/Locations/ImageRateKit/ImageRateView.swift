//
//  ImageRateView.swift
//  Locations
//
//  Created by iem on 09/04/2015.
//  Copyright (c) 2015 JeremyJacquemont. All rights reserved.
//

import UIKit

@IBDesignable
public class ImageRateView: UIView {
    //MARK: - Variables
    var backgroundRingLayer: CAShapeLayer?
    var ringLayer: CAShapeLayer?
    var imageLayer: CALayer?
    @IBInspectable var image: UIImage? {
        didSet{
            updateLayerProperties()
        }
    }
    @IBInspectable var rating: CGFloat = 0.6 {
        didSet{
            updateLayerProperties()
        }
    }
    @IBInspectable var lineWidth: CGFloat = 10.0 {
        didSet{
            updateLayerProperties()
        }
    }
    
    //MARK: - Override Functions
    override public func layoutSubviews() {
        super.layoutSubviews()
        
        //Draw BackgrounRing
        if backgroundRingLayer == nil {
            backgroundRingLayer = CAShapeLayer()
            layer.addSublayer(backgroundRingLayer)
            
            let rect = CGRectInset(bounds, lineWidth / 2.0, lineWidth / 2.0)
            let path = UIBezierPath(ovalInRect: rect)
            
            backgroundRingLayer!.path = path.CGPath
            backgroundRingLayer!.fillColor = nil
            backgroundRingLayer!.lineWidth = lineWidth
            backgroundRingLayer!.strokeColor = UIColor(white: 0.5, alpha: 0.3).CGColor
        }
        backgroundRingLayer!.frame = layer.bounds
        
        //Draw Ring
        if ringLayer == nil {
            ringLayer = CAShapeLayer()
            
            let rect = CGRectInset(bounds, lineWidth / 2.0, lineWidth / 2.0)
            let path = UIBezierPath(ovalInRect: rect)
            let rotate: CGFloat = 3.14 / 2.0
            
            ringLayer!.path = path.CGPath
            ringLayer!.fillColor = nil
            ringLayer!.lineWidth = lineWidth
            ringLayer!.strokeColor = UIColor.redColor().CGColor
            ringLayer!.anchorPoint = CGPointMake(0.5, 0.5)
            ringLayer!.transform = CATransform3DRotate(ringLayer!.transform, -(CGFloat(M_PI))/2, 0, 0, 1)
            
            layer.addSublayer(ringLayer)
        }
        ringLayer!.frame = layer.bounds
        
        //Draw Image
        if imageLayer == nil {
            let imageMaskLayer = CAShapeLayer()
            
            let insetBounds = CGRectInset(bounds, lineWidth + 3.0, lineWidth + 3.0)
            let innerPath = UIBezierPath(ovalInRect: insetBounds)
            
            imageMaskLayer.path = innerPath.CGPath
            imageMaskLayer.fillColor = UIColor.blackColor().CGColor
            imageMaskLayer.frame = bounds
            layer.addSublayer(imageMaskLayer)
            
            imageLayer = CALayer()
            imageLayer!.mask = imageMaskLayer
            imageLayer!.frame = bounds
            imageLayer!.backgroundColor = UIColor.lightGrayColor().CGColor
            imageLayer!.contentsGravity = kCAGravityResizeAspectFill
            layer.addSublayer(imageLayer)
        }
        updateLayerProperties()
    }
    
    //MARK: - Functions
    func updateLayerProperties() {
        if ringLayer != nil {
            ringLayer!.strokeEnd = rating
            
            var strokeColor = UIColor.lightGrayColor()
            switch rating {
            case let r where r >= 0.75:
                strokeColor = UIColor(hue: 112.0/360.0, saturation: 0.39, brightness: 0.85, alpha: 1.0)
            case let r where r >= 0.5:
                strokeColor = UIColor(hue: 208.0/360.0, saturation: 0.51, brightness: 0.75, alpha: 1.0)
            case let r where r >= 0.25:
                strokeColor = UIColor(hue: 40.0/360.0, saturation: 0.39, brightness: 0.85, alpha: 1.0)
            default:
                strokeColor = UIColor(hue: 359.0/360.0, saturation: 0.48, brightness: 0.63, alpha: 1.0)
            }
            
            ringLayer!.strokeColor = strokeColor.CGColor
        }
        
        if imageLayer != nil {
            if let i = image {
                imageLayer!.contents = i.CGImage
            }
        }
    }
    public func setImage(image: UIImage) {
        self.image = image
    }    
    public func setRating(value: CGFloat) {
        self.rating = value
    }

}
