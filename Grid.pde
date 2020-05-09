class Grid { //<>//
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

  void display() {
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

  Cell mouseOn() {
    for (int i = 0; i < 9; i ++) {
      for (int j = 0; j < 9; j ++) {
        if (cells[i][j].mouseOn()) return cells[i][j];
      }
    }
    return null;
  }

  boolean isValid() {
    return checkRows() && checkCols() && checkSquare();
  }

  boolean checkRows() {
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

  boolean checkCols() {
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

  boolean checkSquare() {
    for (int i = 0; i < 9; i += 3) {
      for (int j = 0; j < 9; j += 3) {
        if (checkSquareByTopCorner(i, j) == false) return false;
      }
    }
    return true;
  }

  boolean checkSquareByTopCorner(int i, int j) {
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

  boolean tryAt(Cell c) {
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

  boolean isSolved() {
    for (int i = 0; i < 9; i ++) {
      for (int j = 0; j < 9; j ++) {
        if (cells[i][j].value <= 0) return false;
      }
    }
    return isValid();
  }

  void reset() {
    for (int i = 0; i < 9; i ++) {
      for (int j = 0; j < 9; j ++) {
        cells[i][j].unassign();
      }
    }
  }

  PVector pos(Cell cell) {
    for (int i = 0; i < cells.length; i ++) {
      for (int j = 0; j < cells.length; j ++) {
        if (cells[i][j].equals(cell)) return new PVector(i, j);
      }
    }
    return null;
  }
}
