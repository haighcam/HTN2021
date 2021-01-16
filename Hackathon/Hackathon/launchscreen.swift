//
//  launchscreen.swift
//  Hackathon
//
//  Created by Hanbo Yu on 2021-01-16.
//

import SwiftUI

struct launchscreen: View {
    @State var animate = false
    @State var endSplash = false
    
    var body: some View {

                ZStack{
                    Home()
                    ZStack{
                        Color.white
                    Image("livecap")
                        .resizable()
                        .renderingMode(.original)
                        .frame(width: animate ? nil : 188, height: animate ? nil : 140)
                        .aspectRatio(contentMode: animate ? .fill : .fit)
                        .scaleEffect(animate ? 3:1)
                        .frame(width: UIScreen.main.bounds.width)

                    }
                    .ignoresSafeArea(.all, edges:.all)
                    .onAppear(perform:animateSplash)
                    .opacity(endSplash ? 0 : 1)

                }
    }
    func animateSplash(){
        DispatchQueue.main.asyncAfter(deadline: .now() + 1.75){
            withAnimation(Animation.easeOut(duration:1.5)){
                animate.toggle()
            }
            withAnimation(Animation.easeOut(duration:1.5)){
                endSplash.toggle()
            }
        }
    }
}

struct launchscreen_Previews: PreviewProvider {
    static var previews: some View {
        launchscreen()
    }
}
