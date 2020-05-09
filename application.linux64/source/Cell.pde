class Cell {
  float x, y, w, h;
  int value = 0;
  boolean assigned;

  Cell(float x, float y, float w, float h) {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    assigned = false;
  }

  void display() {
    noFill();
    stroke(0);
    strokeWeight(1);
    rect(x, y, w, h);

    textAlign(CENTER, CENTER);
    textSize(w < h ? w/2 : h/2);
    if (!assigned) fill(0);
    else fill(255, 0, 0);
    if (value > 0) text(value, x + w/2, y + h/2);
  }

  boolean mouseOn() {
    return mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;
  }

  void assignValue(int num) {
    value = num;
    assigned = true;
  }

  void unassign() {
    value  = 0;
    assigned = false;
  }
}
