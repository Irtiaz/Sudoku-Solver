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

  void repeat() {
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

  void reset() {
    grid.reset();
    unassigned = new ArrayList<Cell>();
    solving = false;
    index = 0;
  }
}
