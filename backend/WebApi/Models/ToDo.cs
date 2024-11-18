using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Web;

namespace WebApi.Models
{
    public class ToDo
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int Id { get; set; }

        [Required]
        public int UserId { get; set; }

        [Required]
        public string Task { get; set; }
        [Required]
        public int Priority { get; set; }
        [Required]
        public bool Completed { get; set; }
        [Required]
        public bool LimitedTime { get; set; }
        
        public DateTime DateCreated { get; set; }
        public int DayLimit { get; set; }

    }
}