import javax.swing.JOptionPane;

Sudoku sudoku;
Menu menu;
Info info;
Scene current;
void setup() {
  size(450, 450);
  sudoku = new Sudoku();
  menu = new Menu();
  info = new Info();
  current = menu;
}

void draw() {
  current.repeat();
}

void keyPressed() {
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

void mousePressed() {
  Button b = current.mouseOnButton();
  if (b != null) {
    if (b.message == "Exit") exit();
    if (b.message == "Start") current = sudoku;
    if (b.message == "Instructions") current = info;
    if (b.message == "Back") current = menu;
  }
}
