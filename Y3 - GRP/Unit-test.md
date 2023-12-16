## Unit test
- Interface definition
- Execute path
- Boundary condition
- Error handling
- Local data processing
[Link]https://www.bilibili.com/video/BV1Q64y1z7ht/?spm_id_from=333.1007.top_right_bar_window_history.content.click


### Interface definition
- Can the method be const
- Can the parameter be const
- Can use references or not
- Can variable naming be easy for users to understand at a glance
- Use fewer parameters
- Could something go wrong and how to deal with it

### Execute path
- Try to test all running paths
- Check all conditions, use side-effect-free functions

### Boundary condition
- Equivalence class division of parameters
- Limit parameters

### Error handling
- Illegal parameters
- Create traceable logs (if something went wrong)
- Friendly reminders to the developers

### Local data processing
- Check for unsigned types
- Check for uninitialized variables
- Initialization process
- Overflow check
