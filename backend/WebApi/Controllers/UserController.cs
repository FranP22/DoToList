using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Web.Http.Description;
using WebApi.Models;

namespace WebApi.Controllers
{
    public class UserController : ApiController
    {
        private MojDbContext db = new MojDbContext();

        // GET: api/User
        public IQueryable<User> GetUsers()
        {
            return db.User;
        }

        // GET: api/User/5
        [ResponseType(typeof(User))]
        public IHttpActionResult GetUserByID(int id)
        {
            User user = db.User.Find(id);
            if (user == null)
            {
                return NotFound();
            }

            return Ok(user);
        }

        [ResponseType(typeof(User))]
        public IHttpActionResult GetUserLogin(string username, string password)
        {
            //User user = db.User.Find(id);

            User user = db.User.FirstOrDefault(u => u.Username == username && u.Password == password);

            if (user == null)
            {
                return NotFound();
            }

           return Ok(user);
        }

        [ResponseType(typeof(User))]
        public IHttpActionResult GetUsername(string username)
        {
            //User user = db.User.Find(id);

            User user = db.User.FirstOrDefault(u => u.Username == username);

            if (user == null)
            {
                return NotFound();
            }

            return Ok(user);
        }

        // PUT: api/User/5
        [ResponseType(typeof(void))]
        public IHttpActionResult PutUser(int id, User user)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != user.Id)
            {
                return BadRequest();
            }

            db.Entry(user).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!UserExists(id))
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

        // POST: api/User
        [ResponseType(typeof(User))]
        public IHttpActionResult PostUser(User user)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.User.Add(user);
            db.SaveChanges();

            return CreatedAtRoute("DefaultApi", new { id = user.Id }, user);
        }

        // DELETE: api/User/5
        [ResponseType(typeof(User))]
        public IHttpActionResult DeleteUser(int id)
        {
            User user = db.User.Find(id);
            if (User == null)
            {
                return NotFound();
            }

            db.User.Remove(user);
            db.SaveChanges();

            return Ok(user);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool UserExists(int id)
        {
            return db.User.Count(e => e.Id == id) > 0;
        }

        private bool UserExistsByUsername(string username)
        {
            return db.User.Count(e => e.Username == username) > 0;
        }
    }
}