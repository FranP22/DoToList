using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Web;

namespace WebApi.Models
{
    public class MojDbContext : DbContext
    {
        public DbSet<User> User { get; set; }
        public DbSet<ToDo> ToDo { get; set; }
    }
}