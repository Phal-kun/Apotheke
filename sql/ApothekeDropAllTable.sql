-- Step 1: Disable all foreign key constraints
EXEC sp_MSforeachtable 'ALTER TABLE ? NOCHECK CONSTRAINT ALL';

-- Step 2: Drop all tables
DECLARE @sql NVARCHAR(MAX) = N'';

-- Build the DROP TABLE statements for all tables
SELECT @sql += 'DROP TABLE [' + SCHEMA_NAME(schema_id) + '].[' + name + ']; '
FROM sys.tables;

-- Execute the generated DROP statements
EXEC sp_executesql @sql;

-- Step 3: Re-enable all foreign key constraints (after all tables are dropped, this may not be necessary)
-- This step can be skipped since we dropped all tables, but here’s how it would look:
-- EXEC sp_MSforeachtable 'ALTER TABLE ? WITH CHECK CHECK CONSTRAINT ALL';
