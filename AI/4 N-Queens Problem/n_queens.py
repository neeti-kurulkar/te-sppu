def print_solution(board, N):
    """Function to print the board with queens."""
    for i in range(N):
        row_output = ""
        for j in range(N):
            if board[i][j]:
                row_output += "Q "
            else:
                row_output += "_ "
        print(row_output.strip())
    print()


def can_place_queen(row, col, N, column_used, left_diagonal_used, right_diagonal_used):
    """Check if a queen can be placed safely at (row, col)."""
    if column_used[col]:
        return False
    if left_diagonal_used[row - col + N - 1]:
        return False
    if right_diagonal_used[row + col]:
        return False
    return True


def place_queen(board, row, col, N, column_used, left_diagonal_used, right_diagonal_used):
    """Place a queen at (row, col) and mark the column and diagonals as used."""
    board[row][col] = True
    column_used[col] = True
    left_diagonal_used[row - col + N - 1] = True
    right_diagonal_used[row + col] = True


def remove_queen(board, row, col, N, column_used, left_diagonal_used, right_diagonal_used):
    """Remove a queen from (row, col) and unmark the column and diagonals."""
    board[row][col] = False
    column_used[col] = False
    left_diagonal_used[row - col + N - 1] = False
    right_diagonal_used[row + col] = False


def solve_n_queens_util(board, row, N, column_used, left_diagonal_used, right_diagonal_used):
    """Recursive utility function to solve the N-Queens problem."""
    if row == N:
        print_solution(board, N)
        return True

    solution_found = False

    for col in range(N):
        if can_place_queen(row, col, N, column_used, left_diagonal_used, right_diagonal_used):
            place_queen(board, row, col, N, column_used, left_diagonal_used, right_diagonal_used)

            # Move to the next row
            if solve_n_queens_util(board, row + 1, N, column_used, left_diagonal_used, right_diagonal_used):
                solution_found = True

            # Backtrack if needed
            remove_queen(board, row, col, N, column_used, left_diagonal_used, right_diagonal_used)

    return solution_found


def solve_n_queens(N):
    """Main function to initialize and solve the N-Queens problem."""
    if N >= 10:
        print("Oops! Board size is too large. Please select a smaller value.")
        return

    board = [[False for _ in range(N)] for _ in range(N)]
    column_used = [False for _ in range(N)]
    left_diagonal_used = [False for _ in range(2 * N - 1)]  # row - col + N - 1
    right_diagonal_used = [False for _ in range(2 * N - 1)]  # row + col

    has_solution = solve_n_queens_util(board, 0, N, column_used, left_diagonal_used, right_diagonal_used)

    if not has_solution:
        print("No solution exists.")


# Run the program
N = int(input("Enter the size of the chessboard (N): "))
solve_n_queens(N)