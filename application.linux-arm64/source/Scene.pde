abstract class Scene {
  ArrayList<Button> buttons;  //Every scene contains some buttons
  //But it is not a must to initiate this list where buttons are not needed

  Scene() {
  }

  abstract void repeat();     //Every scene has to define repeat method
  //It is equivalent to the draw function

  Button mouseOnButton() {
    if (buttons != null) {    //If it is null then it means the list has not been initialized. So don't bother
      for (Button b : buttons) {
        if (b.mouseOn()) return b;  //If there is any button under the mouse return that
      }
    }
    return null;  //Otherwise just return null
  }
}
