load ZeroNull.hdl,
output-file ZeroNull.out,
output-list in%B1.16.1 z%B1.1.1 n%B1.1.1 out%B1.16.1;

set in %B0000101011110101;
set z 0, set n 0, eval, output;
set z 1, set n 0, eval, output;
set z 0, set n 1, eval, output;
set z 1, set n 1, eval, output;