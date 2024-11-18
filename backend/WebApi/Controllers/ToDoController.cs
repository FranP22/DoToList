using System;
using System.Collections.Generic;
using System.Data.Entity.Infrastructure;
using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Web.Http.Description;
using WebApi.Models;

namespace WebApi.Controllers
{
    [RoutePrefix("api/ToDo")]
    public class ToDoController : ApiController
    {
        private MojDbContext db = new MojDbContext();

        // GET: api/ToDo
        public IQueryable<ToDo> GetTodos()
        {
            return db.ToDo;
        }

        // GET: api/ToDo/5
        [ResponseType(typeof(ToDo))]
        public IHttpActionResult GetToDoFromUser(int id)
        {
            IEnumerable<ToDo> todo = db.ToDo.Where(td => td.UserId == id);
            if (todo == null)
            {
                return NotFound();
            }

            return Ok(todo);
        }

        // PUT: api/ToDo/5
        [ResponseType(typeof(void))]
        public IHttpActionResult PutToDo(int id, ToDo todo)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != todo.Id)
            {
                return BadRequest();
            }

            db.Entry(todo).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!ToDoExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return StatusCode(HttpStatusCode.NoContent);
        }

        // POST: api/ToDo
        [ResponseType(typeof(ToDo))]
        public IHttpActionResult PostToDo(ToDo todo)
        {
            var user = db.User.Find(todo.UserId);
            if (user == null)
            {
                return BadRequest("User not found.");
            }

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            todo.DateCreated = DateTime.UtcNow;

            db.ToDo.Add(todo);
            db.SaveChanges();

            return CreatedAtRoute("DefaultApi", new { id = todo.Id }, todo);
        }

        // DELETE: api/ToDo/5
        [ResponseType(typeof(ToDo))]
        public IHttpActionResult DeleteToDo(int id)
        {
            ToDo todo= db.ToDo.Find(id);
            if (todo == null)
            {
                return NotFound();
            }

            db.ToDo.Remove(todo);
            db.SaveChanges();

            return Ok(todo);
        }

        [HttpDelete]
        [Route("clear/{userId}")]
        [ResponseType(typeof(ToDo))]
        public IHttpActionResult ClearToDo(int userid)
        {
            User user = db.User.Find(userid);
            if (user == null)
            {
                return NotFound();
            }

            IEnumerable<ToDo> todos = db.ToDo.Where(td => td.UserId == userid && td.Completed == true);
            if(todos == null)
            {
                return NotFound();
            }

            db.ToDo.RemoveRange(todos);
            db.SaveChanges();

            return Ok();
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool ToDoExists(int id)
        {
            return db.ToDo.Count(e => e.Id == id) > 0;
        }
    }
}
