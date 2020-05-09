class Button {
  float x, y, w, h;  //These variables are for displaying the button
  String message; //This is the message that will be shown on the button 

  Button(float x, float y, float w, float h, String st) {
    this.x = x - w/2;      //It is the corner's x location
    this.y = y - h/2;      //It is the corner's y location
    this.w = w;
    this.h = h;
    message = st;
  }

  void display() {
    stroke(0);
    strokeWeight(1);
    if (!mouseOn()) fill(100, 200, 255);    //The fill color changes if there is a mouse on the button
    else fill(200);
    textAlign(CENTER, CENTER);
    textSize(w < h? w/2 : h/2);  //If the smallest side is the width then size is width/2 else height/2

    rect(x, y, w, h);
    fill(0);
    text(message, x + w/2, y + h/2);
  }

  boolean mouseOn() {
    return mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;
  }
}
