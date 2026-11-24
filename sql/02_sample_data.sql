-- Step 0.2: run once (running it twice fails because reg_no is UNIQUE)
USE scd_records;
INSERT INTO students (reg_no, name, department, cgpa) VALUES
('REG-2026-001', 'Ali Khan', 'CS', 3.45),
('REG-2026-002', 'Zarah Ahmed', 'SE', 3.90),
('REG-2026-003', 'Sameer Shah', 'CS', 2.75),
('REG-2026-004', 'Alia Noor', 'AI', 3.60),
('REG-2026-005', 'Maaz Ali', 'SE', 3.10),
('REG-2026-006', 'Peter John', 'DS', 2.40),
('REG-2026-007', 'Sara Malik', 'CS', 3.75),
('REG-2026-008', 'Hamza Qureshi', 'AI', 2.95);

-- Checkpoint: this must show 8 rows
SELECT * FROM students;
