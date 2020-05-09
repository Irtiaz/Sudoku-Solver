import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import javax.swing.JOptionPane; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class SudokuSolverRemake extends PApplet {



Sudoku sudoku;
Menu menu;
Info info;
Scene current;
public void setup() {
  
  sudoku = new Sudoku();
  menu = new Menu();
  info = new Info();
  current = menu;
}

public void draw() {
  current.repeat();
}

public void keyPressed() {
  if (current == sudoku) {
    if (!sudoku.grid.isSolved()) {
      try {
        int num = Integer.parseInt("" + key);
        Cell c = sudoku.grid.selected;
        if (num >= 1 && num <= 9) {
          c.assignValue(num);
        } else if (num == 0) c.unassign();
      }
      catch(Exception e) {
      }
    }

    if (keyCode == ENTER && !sudoku.solving) {
      if (!sudoku.grid.isValid()) JOptionPane.showMessageDialog(null, "Not a valid sudoku");
      else sudoku.solving = true;
    } else if (key == ' ') sudoku.reset();

    if (mouseX == pmouseX && mouseY == pmouseY) {
      if (keyCode == UP) {
        Cell s = sudoku.grid.selected;
        PVector pos = sudoku.grid.pos(s);
        int i = floor(pos.x);
        int j = floor(pos.y);
        if (i > 0) i--;
        sudoku.grid.selected = sudoku.grid.cells[i][j];
      }

      if (keyCode == DOWN) {
        Cell s = sudoku.grid.selected;
        PVector pos = sudoku.grid.pos(s);
        int i = floor(pos.x);
        int j = floor(pos.y);
        if (i < 8) i++;
        sudoku.grid.selected = sudoku.grid.cells[i][j];
      }

      if (keyCode == RIGHT) {
        Cell s = sudoku.grid.selected;
        PVector pos = sudoku.grid.pos(s);
        int i = floor(pos.x);
        int j = floor(pos.y);
        if (j < 8) j++;
        sudoku.grid.selected = sudoku.grid.cells[i][j];
      }

      if (keyCode == LEFT) {
        Cell s = sudoku.grid.selected;
        PVector pos = sudoku.grid.pos(s);
        int i = floor(pos.x);
        int j = floor(pos.y);
        if (j > 0) j--;
        sudoku.grid.selected = sudoku.grid.cells[i][j];
      }
    }
  }
}

public void mousePressed() {
  Button b = current.mouseOnButton();
  if (b != null) {
    if (b.message == "Exit") exit();
    if (b.message == "Start") current = sudoku;
    if (b.message == "Instructions") current = info;
    if (b.message == "Back") current = menu;
  }
}
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

  public void display() {
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

  public boolean mouseOn() {
    return mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;
  }
}
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

  public void display() {
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

  public boolean mouseOn() {
    return mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;
  }

  public void assignValue(int num) {
    value = num;
    assigned = true;
  }

  public void unassign() {
    value  = 0;
    assigned = false;
  }
}
class Grid {
  Cell[][] cells;
  float w, h;
  Cell selected;

  Grid() {
    cells = new Cell[9][9];
    w = width/9;
    h = height/9;
    for (int i = 0; i < 9; i ++) {
      for (int j = 0; j < 9; j ++) {
        cells[i][j] = new Cell(j * w, i * h, w, h);
      }
    }
    selected = cells[0][0];
  }

  public void display() {
    if (mouseX != pmouseX || mouseY != pmouseY) selected = mouseOn();

    if (selected != null) {
      fill(200);
      noStroke();
      rect(selected.x, selected.y, selected.w, selected.h);
    }

    for (int i = 0; i < 9; i ++) {
      for (int j = 0; j < 9; j ++) {
        cells[i][j].display();
      }
    }
    strokeWeight(2);
    line(3*w, 0, 3*w, height);
    line(6*w, 0, 6*w, height);
    line(0, 3*h, width, 3*w);
    line(0, 6*h, width, 6*h);
  }

  public Cell mouseOn() {
    for (int i = 0; i < 9; i ++) {
      for (int j = 0; j < 9; j ++) {
        if (cells[i][j].mouseOn()) return cells[i][j];
      }
    }
    return null;
  }

  public boolean isValid() {
    return checkRows() && checkCols() && checkSquare();
  }

  public boolean checkRows() {
    for (int row = 0; row < 9; row ++) {
      for (int j = 0; j < 9; j ++) {
        for (int k = 0; k < 9; k ++) {
          if (j == k) continue;
          else if (cells[row][j].value > 0 && cells[row][k].value > 0) {
            if (cells[row][j].value == cells[row][k].value) return false;
          }
        }
      }
    }
    return true;
  }

  public boolean checkCols() {
    for (int col = 0; col < 9; col ++) {
      for (int i = 0; i < 9; i ++) {
        for (int k = 0; k < 9; k ++) {
          if (i == k) continue;
          else if (cells[i][col].value > 0 && cells[k][col].value > 0) {
            if (cells[i][col].value == cells[k][col].value) return false;
          }
        }
      }
    }
    return true;
  }

  public boolean checkSquare() {
    for (int i = 0; i < 9; i += 3) {
      for (int j = 0; j < 9; j += 3) {
        if (checkSquareByTopCorner(i, j) == false) return false;
      }
    }
    return true;
  }

  public boolean checkSquareByTopCorner(int i, int j) {
    for (int yoff = 0; yoff < 3; yoff ++) {
      for (int xoff = 0; xoff < 3; xoff ++) {
        Cell c1 = cells[i + yoff][j + xoff];
        for (int yof = 0; yof < 3; yof ++) {
          for (int xof = 0; xof < 3; xof ++) {
            Cell c2 = cells[i + yof][j + xof];
            if (!c1.equals(c2) && c1.value > 0 && c2.value > 0 && c1.value == c2.value) return false;
          }
        }
      }
    }
    return true;
  }

  //void solve() {
  //  ArrayList<Cell> unassigned = new ArrayList<Cell>();
  //  for(int i = 0; i < 9; i ++) {
  //    for(int j = 0; j < 9; j ++) {
  //      if(cells[i][j].assigned == false) unassigned.add(cells[i][j]);
  //    }
  //  }
  //  int index = 0;
  //  while(!isSolved()) {
  //    Cell c = unassigned.get(index);
  //    if(tryAt(c)) index ++;
  //    else index--;
  //  }
  //}

  public boolean tryAt(Cell c) {
    c.value++;
    if (c.value > 9) {
      c.value = 0;
      return false;
    }
    while (!isValid()) {
      c.value++;
      if (c.value > 9) {
        c.value = 0;
        return false;
      }
    }
    return true;
  }

  public boolean isSolved() {
    for (int i = 0; i < 9; i ++) {
      for (int j = 0; j < 9; j ++) {
        if (cells[i][j].value <= 0) return false;
      }
    }
    return isValid();
  }

  public void reset() {
    for (int i = 0; i < 9; i ++) {
      for (int j = 0; j < 9; j ++) {
        cells[i][j].unassign();
      }
    }
  }

  public PVector pos(Cell cell) {
    for (int i = 0; i < cells.length; i ++) {
      for (int j = 0; j < cells.length; j ++) {
        if (cells[i][j].equals(cell)) return new PVector(i, j);
      }
    }
    return null;
  }
}
class Info extends Scene {
  Info() {
    buttons = new ArrayList<Button>();
    Button back = new Button(width/2, height/2, 70, 40, "Back");
    buttons.add(back);
  }

  public void repeat() {
    background(51);
    textSize(16);
    fill(220);
    textAlign(CENTER, CENTER);
    text("Select using mouse or arrow keys", width/2, height/16);
    text("Enter numbers from keyboard and press Enter to solve", width/2, height/16 + 20);
    text("Press space to reset the entire sudoku or\n press 0 to reset the current block", width/2, height/16 + 60);

    textSize(14);
    fill(255);
    text("Developer : Md Irtiaz Kabir\n\nPrevious Version (v1.0) devlopers :\nMd Imtiaz Kabir & Md Irtiaz Kabir", width/2, 3 * height/4);

    for (Button b : buttons) b.display();
  }
}
class Menu extends Scene {

  Menu() {
    buttons = new ArrayList<Button>();
    Button start = new Button(width/2, height/2, 120, 40, "Start");
    Button info = new Button(width/2, height/2+50, 120, 40, "Instructions");
    Button exit = new Button(width/2, height/2+100, 120, 40, "Exit");
    buttons.add(start);
    buttons.add(info);
    buttons.add(exit);
  }

  public void repeat() {
    background(51);
    textSize(50);
    textAlign(CENTER, CENTER);
    fill(34, 177, 76);
    text("Sudoku Solver", width/2, height/8);
    textSize(20);
    text("v2.0", 3 * width/4, height/8 + 40);

    for (Button b : buttons) b.display();
  }
}
abstract class Scene {
  ArrayList<Button> buttons;  //Every scene contains some buttons
  //But it is not a must to initiate this list where buttons are not needed

  Scene() {
  }

  public abstract void repeat();     //Every scene has to define repeat method
  //It is equivalent to the draw function

  public Button mouseOnButton() {
    if (buttons != null) {    //If it is null then it means the list has not been initialized. So don't bother
      for (Button b : buttons) {
        if (b.mouseOn()) return b;  //If there is any button under the mouse return that
      }
    }
    return null;  //Otherwise just return null
  }
}
class Sudoku extends Scene {
  Grid grid;
  ArrayList<Cell> unassigned;
  boolean solving;
  int index;

  Sudoku() {
    grid = new Grid();
    unassigned = new ArrayList<Cell>();
    solving = false;
    index = 0;
  }

  public void repeat() {
    background(255);
    grid.display();
    if (solving) {

      if (unassigned.size() == 0) {
        for (int i = 0; i < 9; i ++) {
          for (int j = 0; j < 9; j ++) {
            if (grid.cells[i][j].assigned == false) unassigned.add(grid.cells[i][j]);
          }
        }
      }

      for (int attempt = 0; attempt < 1000; attempt ++) {
        if (!grid.isSolved()) {
          Cell c = unassigned.get(index);
          if (grid.tryAt(c)) index ++;
          else index --;
        }
      }
    }
  }

  public void reset() {
    grid.reset();
    unassigned = new ArrayList<Cell>();
    solving = false;
    index = 0;
  }
}
  public void settings() {  size(450, 450); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "SudokuSolverRemake" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
