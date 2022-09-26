using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Carer.Model.Common
{
    public class BaseModel
    {
        private Guid id;
        private DateTime dateCreated;
        private DateTime dateUpdated;

        public Guid Id { get => id; set => id = value; }
        public DateTime DateCreated { get => dateCreated; set => dateCreated = value; }
        public DateTime DateUpdated { get => dateUpdated; set => dateUpdated = value; }

        public void generate()
        {
            id = Guid.NewGuid();
            dateCreated = DateTime.UtcNow;
            dateUpdated = DateTime.UtcNow;
        }
    }
}
