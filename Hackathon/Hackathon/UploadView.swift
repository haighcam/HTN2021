//
//  UploadView.swift
//  Hackathon
//
//  Created by Hanbo Yu on 2021-01-16.
//

import SwiftUI

struct UploadView: View {
    @State var showImagePickerView: Bool = false
    @State var image : UIImage? = nil
    @State var text : String? = nil
    var body: some View {
        VStack{
            if image != nil{
                Image(uiImage: image!)
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .padding(.vertical, CGFloat(10))
            }
            if text != nil{
                Text(text!)
            }
            Button(action:{
                self.showImagePickerView = true
            }){
                HStack{
                    Text("Pick Image")
                }
            }.foregroundColor(.white)
            .background(Color.blue)
            .cornerRadius(40)
            .padding(10)
            .sheet(isPresented: self.$showImagePickerView){
                ImagePicker(image: self.$image,text:self.$text)
            }
        }
    }
}

struct UploadView_Previews: PreviewProvider {
    static var previews: some View {
        UploadView()
    }
}
