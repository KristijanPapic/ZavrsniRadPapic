using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Carer.Model
{
    public class SimpleAppliedUser
    {
        private Guid userId;
        private string userName;

        public Guid UserId { get => userId; set => userId = value; }
        public string UserName { get => userName; set => userName = value; }
    }
}
